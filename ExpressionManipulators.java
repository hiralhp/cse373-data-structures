// Hiral Patel and Christofer Conrad Forbes

package calculator.ast;


import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;

import datastructures.interfaces.IList;

public class ExpressionManipulators {
    /**
    * Takes the given AstNode node and attempts to convert it into a double.
    *
    * Returns a number AstNode containing the computed double.
    *
    * @throws EvaluationError  if any of the expressions contains an undefined variable.
    * @throws EvaluationError  if any of the expressions uses an unknown operation.
    */
    public static AstNode toDouble(Environment env, AstNode node) {
        return new AstNode(toDoubleHelper(env.getVariables(), node));
    }
    
    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        
        if (node.isNumber()) {
            return node.getNumericValue();
        } else if (node.isVariable()) {
            if (!variables.containsKey(node.getName())) {
                // If the expression contains an undefined variable, we give up.
                throw new EvaluationError("Undefined variable: " + node.getName());
            }
            
            AstNode result = variables.get(node.getName());
            return (toDoubleHelper(variables, result));
          
        } else {
            String name = node.getName();
            
            if (name.equals("+")) {
                return (toDoubleHelper(variables, node.getChildren().get(0)) + toDoubleHelper(variables, node.getChildren().get(1)));
            }
            
            else if (name.equals("-")) {
                return (toDoubleHelper(variables, node.getChildren().get(0)) - toDoubleHelper(variables, node.getChildren().get(1)));
            }
            
            else if (name.equals("*")) {
                return (toDoubleHelper(variables, node.getChildren().get(0)) * toDoubleHelper(variables, node.getChildren().get(1)));
            }
            
            else if (name.equals("/")) {
                return (toDoubleHelper(variables, node.getChildren().get(0)) / toDoubleHelper(variables, node.getChildren().get(1)));
            }
            
            else if (name.equals("^")) {
                return (Math.pow(toDoubleHelper(variables, node.getChildren().get(0)), toDoubleHelper(variables, node.getChildren().get(1))));
                
                
            } else if (name.equals("negate")) {
                return (-1*(toDoubleHelper(variables, node.getChildren().get(0))));
            } else if (name.equals("sin")) {
                return (Math.sin((toDoubleHelper(variables, node.getChildren().get(0)))));
                
            } else if (name.equals("cos")) {
                return (Math.cos((toDoubleHelper(variables, node.getChildren().get(0)))));
                
            } else if (name.equals("toDouble")) {
                return toDoubleHelper(variables,node.getChildren().get(0));
            }
            else { //unknown operation, we give up
                throw new EvaluationError("Unknown operation: " + name);
            }
        }
    }
    
    public static AstNode simplify(Environment env, AstNode node) {
        node = node.getChildren().get(0);
        AstNode simplifiedNumbers = node;
        
        simplifiedNumbers = simplifyHelper(env, env.getVariables(), node);
        
        return simplifiedNumbers;
    }
    
    private static AstNode simplifyHelper(Environment env, IDictionary<String, AstNode> list, AstNode node) {
        IList<AstNode> childList = node.getChildren();
        if(node.isNumber()) {
            return node;
        }
        else if(node.isVariable()) {
            if(env.getVariables().containsKey(node.getName())) {
                node = list.get(node.getName());
                node = simplifyHelper(env, list, node);
            }
            return node;
        } else {
            IList<AstNode> newChildren = new DoubleLinkedList<AstNode>();
            String name = node.getName();
            if(name.equals("+") || name.equals("-") ||
            name.equals("*")) {
                if(childList.get(0).isNumber() && childList.get(1).isNumber()) {
                    node = toDouble(env, node);
                    return node;
                } else {
                    AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                    AstNode rightChild = simplifyHelper(env, list, childList.get(1));
                    newChildren.add(leftChild);
                    newChildren.add(rightChild);
                    return new AstNode(node.getName(), newChildren);
                }
            }
            
            else if(name.equals("/") || name.equals("^")) {
                IList<AstNode> newChildren2 = new DoubleLinkedList<AstNode>();
                if(!childList.get(0).isNumber() && !childList.get(1).isNumber()) {
                    AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                    AstNode rightChild  = simplifyHelper(env, list, childList.get(1));
                    newChildren2.add(leftChild);
                    newChildren2.add(rightChild);
                    return new AstNode(node.getName(), newChildren2);
                }
                
            } else if(name.equals("negate") || name.equals("sin") || name.equals("cos")){
                IList<AstNode> newChildren3 = new DoubleLinkedList<AstNode>();
                AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                newChildren3.add(leftChild);
                return new AstNode(node.getName(), newChildren3);
            } else if(name.equals(":=")){
            		return simplifyHelper(env, list, childList.get(0));
            }
            
        }
        return node;
    }
    
    
    /**
    * Expected signature of plot:
    *
    * >>> plot(exprToPlot, var, varMin, varMax, step)
    *
    * Example 1:
    *
    * >>> plot(3 * x, x, 2, 5, 0.5)
    *
    * This command will plot the equation "3 * x", varying "x" from 2 to 5 in 0.5
    * increments. In this case, this means you'll be plotting the following points:
    *
    * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
    *
    * ---
    *
    * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
    * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
    *
    * >>> c := 4
    * 4
    * >>> step := 0.01
    * 0.01
    * >>> plot(a^2 + c*a + a, a, -10, 10, step)
    *
    * ---
    *
    * @throws EvaluationError  if any of the expressions contains an undefined variable.
    * @throws EvaluationError  if varMin > varMax
    * @throws EvaluationError  if 'var' was already defined
    * @throws EvaluationError  if 'step' is zero or negative
    */
    // Hiral Patel and Christofer Conrad Forbes
    
    package calculator.ast;
    
    
    import calculator.interpreter.Environment;
    import calculator.errors.EvaluationError;
    import datastructures.concrete.DoubleLinkedList;
    import datastructures.interfaces.IDictionary;
    
    import datastructures.interfaces.IList;
    
    public class ExpressionManipulators {
        /**
         * Takes the given AstNode node and attempts to convert it into a double.
         *
         * Returns a number AstNode containing the computed double.
         *
         * @throws EvaluationError  if any of the expressions contains an undefined variable.
         * @throws EvaluationError  if any of the expressions uses an unknown operation.
         */
        public static AstNode toDouble(Environment env, AstNode node) {
            return new AstNode(toDoubleHelper(env.getVariables(), node));
        }
        
        private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
            
            if (node.isNumber()) {
                return node.getNumericValue();
            } else if (node.isVariable()) {
                if (!variables.containsKey(node.getName())) {
                    // If the expression contains an undefined variable, we give up.
                    throw new EvaluationError("Undefined variable: " + node.getName());
                }
                
                AstNode result = variables.get(node.getName());
                return (toDoubleHelper(variables, result));
                
            } else {
                String name = node.getName();
                
                if (name.equals("+")) {
                    return (toDoubleHelper(variables, node.getChildren().get(0)) + toDoubleHelper(variables, node.getChildren().get(1)));
                }
                
                else if (name.equals("-")) {
                    return (toDoubleHelper(variables, node.getChildren().get(0)) - toDoubleHelper(variables, node.getChildren().get(1)));
                }
                
                else if (name.equals("*")) {
                    return (toDoubleHelper(variables, node.getChildren().get(0)) * toDoubleHelper(variables, node.getChildren().get(1)));
                }
                
                else if (name.equals("/")) {
                    return (toDoubleHelper(variables, node.getChildren().get(0)) / toDoubleHelper(variables, node.getChildren().get(1)));
                }
                
                else if (name.equals("^")) {
                    return (Math.pow(toDoubleHelper(variables, node.getChildren().get(0)), toDoubleHelper(variables, node.getChildren().get(1))));
                    
                    
                } else if (name.equals("negate")) {
                    return (-1*(toDoubleHelper(variables, node.getChildren().get(0))));
                } else if (name.equals("sin")) {
                    return (Math.sin((toDoubleHelper(variables, node.getChildren().get(0)))));
                    
                } else if (name.equals("cos")) {
                    return (Math.cos((toDoubleHelper(variables, node.getChildren().get(0)))));
                    
                } else if (name.equals("toDouble")) {
                    return toDoubleHelper(variables,node.getChildren().get(0));
                }
                else { //unknown operation, we give up
                    throw new EvaluationError("Unknown operation: " + name);
                }
            }
        }
        
        public static AstNode simplify(Environment env, AstNode node) {
            node = node.getChildren().get(0);
            AstNode simplifiedNumbers = node;
            
            simplifiedNumbers = simplifyHelper(env, env.getVariables(), node);
            
            return simplifiedNumbers;
        }
        
        private static AstNode simplifyHelper(Environment env, IDictionary<String, AstNode> list, AstNode node) {
            IList<AstNode> childList = node.getChildren();
            if(node.isNumber()) {
                return node;
            }
            else if(node.isVariable()) {
                if(env.getVariables().containsKey(node.getName())) {
                    node = list.get(node.getName());
                    node = simplifyHelper(env, list, node);
                }
                return node;
            } else {
                IList<AstNode> newChildren = new DoubleLinkedList<AstNode>();
                String name = node.getName();
                if(name.equals("+") || name.equals("-") ||
                   name.equals("*")) {
                    if(childList.get(0).isNumber() && childList.get(1).isNumber()) {
                        node = toDouble(env, node);
                        return node;
                    } else {
                        AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                        AstNode rightChild = simplifyHelper(env, list, childList.get(1));
                        newChildren.add(leftChild);
                        newChildren.add(rightChild);
                        return new AstNode(node.getName(), newChildren);
                    }
                }
                
                else if(name.equals("/") || name.equals("^")) {
                    IList<AstNode> newChildren2 = new DoubleLinkedList<AstNode>();
                    if(!childList.get(0).isNumber() && !childList.get(1).isNumber()) {
                        AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                        AstNode rightChild  = simplifyHelper(env, list, childList.get(1));
                        newChildren2.add(leftChild);
                        newChildren2.add(rightChild);
                        return new AstNode(node.getName(), newChildren2);
                    }
                    
                } else if(name.equals("negate") || name.equals("sin") || name.equals("cos")){
                    IList<AstNode> newChildren3 = new DoubleLinkedList<AstNode>();
                    AstNode leftChild = simplifyHelper(env, list, childList.get(0));
                    newChildren3.add(leftChild);
                    return new AstNode(node.getName(), newChildren3);
                } else if(name.equals(":=")){
                    return simplifyHelper(env, list, childList.get(0));
                }
                
            }
            return node;
        }
        
        
        /**
         * Expected signature of plot:
         *
         * >>> plot(exprToPlot, var, varMin, varMax, step)
         *
         * Example 1:
         *
         * >>> plot(3 * x, x, 2, 5, 0.5)
         *
         * This command will plot the equation "3 * x", varying "x" from 2 to 5 in 0.5
         * increments. In this case, this means you'll be plotting the following points:
         *
         * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
         *
         * ---
         *
         * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
         * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
         *
         * >>> c := 4
         * 4
         * >>> step := 0.01
         * 0.01
         * >>> plot(a^2 + c*a + a, a, -10, 10, step)
         *
         * ---
         *
         * @throws EvaluationError  if any of the expressions contains an undefined variable.
         * @throws EvaluationError  if varMin > varMax
         * @throws EvaluationError  if 'var' was already defined
         * @throws EvaluationError  if 'step' is zero or negative
         */
        public static AstNode plot(Environment env, AstNode node) {
            
            // Note: every single function we add MUST return an
            // AST node that your "simplify" function is capable of handling.
            // However, your "simplify" function doesn't really know what to do
            // with "plot" functions (and what is the "plot" function supposed to
            // evaluate to anyways?) so we'll settle for just returning an
            // arbitrary number.
            //
            // When working on this method, you should uncomment the following line:
            //
            AstNode expr = node.getChildren().get(0);
            AstNode var = node.getChildren().get(1);
            AstNode varMin = node.getChildren().get(2);
            AstNode varMax = node.getChildren().get(3);
            AstNode step = node.getChildren().get(4);
            
            /*
             undefinedVariableErrorCheck(env.getVariables(), expr.getChildren().get(0));
             undefinedVariableErrorCheck(env.getVariables(), var.getChildren().get(0));
             undefinedVariableErrorCheck(env.getVariables(), varMin.getChildren().get(0));
             undefinedVariableErrorCheck(env.getVariables(), varMax.getChildren().get(0));
             undefinedVariableErrorCheck(env.getVariables(), step.getChildren().get(0));
             */
            
            if (env.getVariables().containsKey(var.getName())) {
                throw new EvaluationError(var + " is already defined");
            }
            if (varMin.isNumber() && varMax.isNumber() && (varMin.getNumericValue() > varMax.getNumericValue())) {
                throw new EvaluationError("varMin > varMax");
            }
            if (step.isNumber() && step.getNumericValue() <= 0) {
                throw new EvaluationError("step must be > 0");
            }
            
            IList<Double> xValues = new DoubleLinkedList<Double>();
            IList<Double> yValues = new DoubleLinkedList<Double>();
            
            for (double i = varMin.getNumericValue(); i <= varMax.getNumericValue(); i += step.getNumericValue()) {
                env.getVariables().put(var.getName(), new AstNode(i));
                xValues.add(i);
                yValues.add(toDoubleHelper(env.getVariables(), expr));
                env.getVariables().remove(var.getName());
            }
            
            env.getImageDrawer().drawScatterPlot("Plot", var.getName(), "output", xValues, yValues);
            return new AstNode(1);
        }
        
        private static void undefinedVariableErrorCheck(IDictionary<String, AstNode> variables, AstNode node) {
            if (node != null) {
                if (node.isVariable() && !variables.containsKey(node.getName())) {
                    throw new EvaluationError("Unknown variable: " + node.getName());
                }
                undefinedVariableErrorCheck(variables, node.getChildren().get(0));
                if (node.getChildren().size() == 2)
                    undefinedVariableErrorCheck(variables, node.getChildren().get(1));
            }
            
        }
        
        
    }
