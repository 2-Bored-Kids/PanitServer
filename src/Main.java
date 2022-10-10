public class Main {
    public static void main(String[] args){
        try{
            Integer.parseInt(args[0]);
        }catch (Exception e){
            System.out.println("Bitte gebe eine gültige Zahl an!!");
        }
        new panitServer(Integer.parseInt(args[0]));
    }
}
