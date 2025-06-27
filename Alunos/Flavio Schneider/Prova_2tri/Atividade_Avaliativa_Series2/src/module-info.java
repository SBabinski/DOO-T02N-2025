// module-info.java
module Atividade_Avaliativa_Series {
    
    requires com.google.gson;
    requires java.base; // Geralmente implícito ou adicionado para APIs padrão do Java

    
    opens classes to com.google.gson;
}
