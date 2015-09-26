package br.com.laboratorio.hemope.Erro;

/**
 * Created by User on 18/09/2015.
 */
public class HemopeException extends Exception {
    private String msg;
    public HemopeException(String msg){
        super(msg);
        this.msg = msg;
    }
    public class ArgumentoInvalidoException extends HemopeException{
        public ArgumentoInvalidoException(){
            super("Argumento invalido");
        }
    }

    public class ArgumentoNuloException extends HemopeException{
        public ArgumentoNuloException(){
            super("Argumento nulo");
        }
    }
}
