package noka;

public interface Kerpich {
     void print();
     default void def(){
         System.out.println("def");
     }
      static void kek(){
         System.out.println("kek");
     }
}
