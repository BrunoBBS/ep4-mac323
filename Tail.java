/* ***********************************************************************
 *  Compilation:  javac -Xlint Tail.java
 *  Execution:    java Tail
 *  Dependencies: StdIn.java StdOut.java
 *
 *
 * % java Tail testes/*
 * ==> testes/ind.txt<==
 * changed for light and transient causes; and accordingly all experience
 * hath shewn, that mankind are more disposed to suffer, while evils are
 * sufferable, than to right themselves by abolishing the forms to which
 * they are accustomed. But when a long train of abuses and usurpations,
 * pursuing invariably the same Object evinces a design to reduce them
 * under absolute Despotism, it is their right, it is their duty, to
 * throw off such Government, and to provide new Guards for their future
 * security.--Such has been the patient sufferance of these Colonies; and
 * such is now the necessity which constrains them to alter their former
 * Systems of Government.
 *
 * ==> testes/les-miserables.txt<==
 *
 *
 *  Most people start at our Web site which has the main PG search facility:
 *
 *       http://www.gutenberg.org
 *
 *  This Web site includes information about Project Gutenberg-tm,
 *  including how to make donations to the Project Gutenberg Literary
 *  Archive Foundation, how to help produce our new eBooks, and how to
 *  subscribe to our email newsletter to hear about new eBooks.
 *
 *  ==> testes/teste1.txt<==
 *  aaa bbbb aa aaa ccc dd
 *
 *  ==> testes/teste2.txt<==
 *  Como é bom estudar MAC0323!
 *  Como é bom estudar MAC0323!
 *
 *
 *************************************************************************/

// This class provides methods for reading strings and numbers from standard
// input, file input, URLs, and sockets.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
import edu.princeton.cs.algs4.In;

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/* Está é classe é inspirada no Tarefa 04 do Paulo Felofiloff para
 * a edição de 2014 de MAC0323:
 *
 *     https://www.ime.usp.br/~pf/mac0323-2014/tarefas/T04.html
 *
 * Como diz a tarefa
 *
 *    "Resolva o problema sem desperdiçar memória.
 *     Use uma (ou mais) das ADTs que já estudamos."
 *
 */

public class Tail {
    // atributos de estado

    // Indice da ultima linha no vetro circular. Vai de 0 a k-1.
    int lastIndex = 0, nLines = 10;
    // Vetor circular usado para guardar strings correspondentes a cada linha do
    // texto.
    String[] buffer;

    /*
     * Construtores.
     */
    // Construtor que prepara para que o método lines()
    // retorne um objeto iterável com as 10 últimas linhas
    // do arquivo fileName. O valor 10 é o default.
    public Tail(String fileName) {
        buffer = new String[nLines];
        In in = new In(fileName);
        while (in.hasNextLine()) {
            buffer[lastIndex] =  in.readLine();
            lastIndex = (lastIndex == nLines - 1) ? lastIndex + 1 : 0;
        }
    }

    // Construtor que prepara para que o método lines()
    // retorne um objeto iterável com as k últimas linhas
    // do arquivo fileName.
    public Tail(String fileName, int k) {
        nLines = k;
        buffer = new String[nLines];
        In in = new In(fileName);
        while (in.hasNextLine()) {
            buffer[lastIndex] =  in.readLine();
            lastIndex = (lastIndex + 1) % k;
        }
    }

    /* Returns an iterable object containing the last k lines.
     * The value of k depends on the constructor.
     */
    public Iterable<String> lines() {
        return new Iterable<String> () {

            public Iterator<String> iterator() {

                return new Iterator<String> () {

                    private int index = lastIndex + 1;

                    public boolean hasNext() {
                        return index < nLines;
                    }

                    public String  next() {
                        return buffer[index++];
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }

        };
    }

    /*========================================================================
     *  métodos privados
     *=======================================================================*/

    // apenas para o uso do unit test abaixo
    private static void use() {
        String message = "Use: java Tail [-n NUM] [FILE]\n" +
            "   Print the last 10 lines of each FILE to standard output.\n" +
            "   With more than one FILE, precede each with a header giving the file name.\n" +
            "   -n NUM: output the last NUM lines, instead of the last 10";
        StdOut.println(message);
    }

    /*
     * Unit test
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            use();
            return;
        }

        if (args[0].equals("-n")) {
            int k = Integer.parseInt(args[1]);
            for (int i = 2;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i], k);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }

        }
        else {
            for (int i = 0;  i < args.length; i++) {
                StdOut.println("==> " + args[i] + "<==");
                Tail tail = new Tail(args[i]);
                for (String line: tail.lines()) {
                    StdOut.println(line);
                }
                StdOut.println();
            }
        }
    }
}

