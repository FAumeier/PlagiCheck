package framework;


import java.io.PrintWriter;

/**
 * Created by Matthias on 18.03.2016.
 */
class PlagiCheck {
    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            AlignmentController controller = new AlignmentController(args[0], args[1]);
            controller.run();
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("-std")) {
                try(  PrintWriter out = new PrintWriter( "original.txt" )  ){
                    out.println( args[1] );
                }
                try(  PrintWriter out = new PrintWriter( "suspect.txt" )  ){
                    out.println( args[2] );
                }
                AlignmentController controller = new AlignmentController("original.txt", "suspect.txt");
                controller.run();
            } else {
                throw new Exception("Only avialable Option is -std input1 input2");
            }
        } else {
            throw new Exception("Wir erwarten den Namen des Originals und den Namen des Plagiats");
        }
    }
}

