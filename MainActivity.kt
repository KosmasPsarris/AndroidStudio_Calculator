package com.p17191.ergasies.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false;
    var lastDot = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // CLR button clears the screen of calculator
        buttonClear.setOnClickListener {

        textView.text = "";
        lastNumeric = false;
        lastDot = false;
        }

        // can only put "." only if before it was a number and there is no other "."
        buttonDecimal.setOnClickListener {

            if(lastNumeric && !lastDot){
                textView.append(".")
                lastNumeric = false;
                lastDot = true;
            }
        }

        // result of calculations
        buttonEqual.setOnClickListener {

            // last digit must be numeric in order to calculate
            if(lastNumeric){
                var calcValue = textView.text.toString();
                var prefix = "";

                try {
                    //if equation starts with "-" we need to ignore it before splitting so no errors would appear
                    if(calcValue.startsWith("-")){
                        prefix = "-";
                        calcValue = calcValue.substring(1);
                    }
                    //if operator is "-"
                    if(calcValue.contains("-")){
                        //split the equation in 2 parts/numbers
                        val splitValue = calcValue.split("-");

                        var leftNumber = splitValue[0];
                        val rightNumber = splitValue[1];

                        if(!prefix.isEmpty()){
                             leftNumber = prefix + leftNumber; //add minus in front
                        }
                        removeZeros((leftNumber.toDouble() - rightNumber.toDouble()).toString());

                    } //if operator is "+"
                    else if(calcValue.contains("+")){
                        //split the equation in 2 parts/numbers
                        val splitValue = calcValue.split("+");

                        val leftNumber = splitValue[0];
                        val rightNumber = splitValue[1];

                        removeZeros((leftNumber.toDouble() + rightNumber.toDouble()).toString());

                    } //if operator is "/"
                    else if(calcValue.contains("/")){
                        //split the equation in 2 parts/numbers
                        val splitValue = calcValue.split("/");

                        val leftNumber = splitValue[0];
                        val rightNumber = splitValue[1];

                        removeZeros((leftNumber.toDouble() / rightNumber.toDouble()).toString());

                    } //if operator is "*"
                    else if(calcValue.contains("*")){
                        //split the equation in 2 parts/numbers
                        val splitValue = calcValue.split("*");

                        val leftNumber = splitValue[0];
                        val rightNumber = splitValue[1];

                        removeZeros((leftNumber.toDouble() * rightNumber.toDouble()).toString());
                    }
                }catch (e:ArithmeticException){
                    e.printStackTrace()
                }
            }
        }
    }

    //runs with each button click
    fun OnDigit(view : View){

        //get text of button pressed
        textView.append((view as? Button)?.text);
        lastNumeric = true;
    }

    fun onOperator(view: View){

        if(lastNumeric && !isOperatorAdded(textView.text.toString())){

            textView.append((view as Button).text)
            lastNumeric = false;
            lastDot = false;
        }
    }

    private fun isOperatorAdded(value : String) : Boolean{
        // if "-" is the first digit then it means the number is negative and it is not considered as an operator
        return if(value.startsWith("-")) {
            false
        }
        else //return true if we find an operator
        {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeros(result : String){
        var resultValue = result;
        //remove zeros from the right side of the result calculation and also the decimal "." operator if it has nothing on it's right
        while(true){
            //first check if the only number is 0 or if digit is not zero AND NOT decimal , if yes it means the result of the  calculation is 0
            //or that there is a non zero number we can leave as is.
            if(resultValue[resultValue.length - 1] == '0' && resultValue.length == 1
                || resultValue[resultValue.length - 1] != '0' && resultValue[resultValue.length - 1] != '.'){
                break;
            }
            else{ //else remove last character
                resultValue = resultValue.substring(0, resultValue.length - 1);
            }
        }
        textView.text = resultValue;
    }
}







