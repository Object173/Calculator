package com.object173.calculator.main;

import com.object173.calculator.calc.Calculable;
import com.object173.calculator.calc.Calculator;
import com.object173.calculator.model.Bracket;
import com.object173.calculator.model.Constant;
import com.object173.calculator.model.OperationRepository;
import com.object173.calculator.model.Operator;
import com.object173.calculator.resources.ResourcesManager;
import com.object173.calculator.resources.StringResKey;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.text.ParseException;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.CompletableFuture;

public class MainController {

    @FXML private FlowPane operatorButtonPane;
    @FXML private Button buttonSubmit;
    @FXML private TextField expressionTextField;

    private ResourcesManager mResManager;
    private Calculable mCalculator;
    private boolean mIsCalculating = false;


    private static class CalcResult {
        final double result;
        final Exception exception;

        CalcResult(final double result) {
            this.result = result;
            this.exception = null;
        }

        CalcResult(final Exception exception) {
            this.exception = exception;
            this.result = 0d;
        }
    }

    @FXML
    public void initialize(){
        mResManager = ResourcesManager.getInstance();

        final Operator[] operators = OperationRepository.getInstance().getOperators();
        final Bracket[] brackets = OperationRepository.getInstance().getBrackets();
        final Constant[] constants = OperationRepository.getInstance().getConstants();

        mCalculator = new Calculator(brackets, operators, constants);
        initOperatorPane(operators, brackets, constants);
        buttonSubmit.setOnAction(event -> submit());
    }

    @FXML
    public void onKeyPressed(final KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                submit();
                break;
            case BACK_SPACE:
                expressionTextField.deletePreviousChar();
                break;
        }
    }

     @FXML
     public void onButtonClick(final ActionEvent event) {
         expressionTextField.appendText(((Button)event.getSource()).getText());
     }

    private void submit() {
        final String input = expressionTextField.getText();
        if(input == null || input.isEmpty() || mIsCalculating) {
            return;
        }

        setIsCalculating(true);
        final CompletableFuture<CalcResult> calculateTask = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return new CalcResult(
                                mCalculator.parse(expressionTextField.getText())
                                        .calculate());
                    }
                    catch (Exception e) {
                        return new CalcResult(e);
                    }
                });
        calculateTask.thenAccept(result -> Platform.runLater(() -> setCalcResult(result)));
    }

     private void setIsCalculating(final boolean flag) {
        mIsCalculating = flag;
        buttonSubmit.setDisable(flag);
     }

     private void setCalcResult(final CalcResult result) {
         setIsCalculating(false);

         if(result.exception == null) {
            expressionTextField.setText(Double.toString(result.result));
            return;
        }

         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText(null);

         if(result.exception instanceof UnsupportedOperationException) {
             alert.setContentText(String.format(
                     mResManager.getString(StringResKey.ERROR_UNSUPPORTED_OPERATION),
                     result.exception.getMessage()));
         }
         else
         if(result.exception instanceof IOException) {
             alert.setContentText(mResManager.getString(StringResKey.ERROR_READ_EXPRESSION));
         }
         else
         if(result.exception instanceof ParseException) {
            final ParseException parseException = (ParseException)result.exception;
            if(parseException.getMessage() == null) {
                alert.setContentText(mResManager.getString(StringResKey.ERROR_INVALID_EXPRESSION));
            }
            else {
                alert.setContentText(String.format(
                            mResManager.getString(StringResKey.ERROR_UNKNOWN_OPERATOR),
                            parseException.getMessage()));
            }
         }
         else
         if(result.exception instanceof MissingFormatArgumentException) {
             alert.setContentText(mResManager.getString(StringResKey.ERROR_MISSING_OPERATOR));
         }
         else {
             throw new RuntimeException(result.exception);
         }

         alert.showAndWait();
     }

    private void initOperatorPane(final Operator[] operators, final Bracket[] brackets, final Constant[] constants) {
        Button button;
        for(Bracket bracket : brackets) {
            button = createButton(bracket.startKey);
            operatorButtonPane.getChildren().add(button);
            button = createButton(bracket.endKey);
            operatorButtonPane.getChildren().add(button);
        }
        for(Operator operator : operators) {
            button = createButton(operator.key);
            operatorButtonPane.getChildren().add(button);
        }
        for(Constant constant : constants) {
            button = createButton(constant.key);
            operatorButtonPane.getChildren().add(button);
        }
    }

    private Button createButton(final String title) {
        final Button button = new Button(title);
        button.setPrefSize(buttonSubmit.getPrefWidth(), buttonSubmit.getPrefHeight());
        button.setStyle(buttonSubmit.getStyle());
        button.setOnAction(this::onButtonClick);
        return button;
    }
}
