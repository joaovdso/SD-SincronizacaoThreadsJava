[Voltar Inicio](../README.md)

### Sincronização usando Monitores
Um monitor pode ser visualizado como um inibidor ou um token que uma thread pode adquirir para obter acesso exclusivo a um recurso. Essa metáfora é útil para entender o seu funcionamento:

##### Associação com Objetos
* Cada monitor está vinculado a um objeto específico em Java. Isso significa que o monitor age como uma tranca nesse objeto, controlando o acesso a seus métodos e atributos.
* **Exclusão Mútua**: Quando uma thread adquire o monitor de um objeto, ela obtém o direito exclusivo de acesso a esse objeto. Nenhuma outra thread pode adquirir o mesmo monitor enquanto a primeira estiver utilizando-o. Essa propriedade é conhecida como exclusão mútua e é essencial para evitar condições de corrida e outros problemas de concorrência.
* **Aquisição e Liberação**: A aquisição do monitor ocorre de forma automática quando uma thread entra em uma região crítica de código protegida pela palavra-chave synchronized. A liberação ocorre implicitamente ao final do bloco sincronizado.
* **Regiões Críticas**: é um segmento de código que acessa recursos compartilhados e, portanto, requer proteção para evitar que múltiplas threads modifiquem esses recursos simultaneamente. Em Java, as regiões críticas são delimitadas pela palavra-chave synchronized.

Exemplo:
```java
public class ContaBancaria {
    private int saldo;

    public synchronized void sacar(int valor) {
        if (saldo >= valor) {
            saldo -= valor;
        } else {
            // Saldo insuficiente
        }
    }
}
```
* No exemplo acima, o método sacar é sincronizado. Isso significa que apenas uma thread por vez pode executar esse método. Se uma thread estiver executando sacar, qualquer outra thread que tentar chamar esse método será bloqueada até que a primeira termine. Isso garante que o saldo da conta seja atualizado de forma consistente, evitando problemas como saldos negativos.

##### Benefícios da Sincronização com Monitores
* **Prevenção de Condições de Corrida**: A sincronização evita que múltiplas threads acessem e modifiquem os mesmos dados simultaneamente, eliminando a possibilidade de resultados imprevisíveis.
* **Garantia da Consistência dos Dados**: Ao garantir que apenas uma thread por vez possa acessar um recurso compartilhado, a sincronização mantém a integridade dos dados.
* **Facilidade de Uso**: A palavra-chave synchronized oferece uma forma simples e direta de proteger regiões críticas em Java.
  
##### Considerações Importantes
* **Bloqueio**: A sincronização pode levar ao bloqueio de threads, o que pode afetar o desempenho da aplicação. É importante utilizar a sincronização de forma estratégica para evitar gargalos.
* **Deadlocks**: A sincronização inadequada pode levar a deadlocks, situações em que duas ou mais threads ficam bloqueadas indefinidamente esperando por um recurso que outra thread está segurando.
* **Alternativas**: Além dos monitores, Java oferece outros mecanismos de sincronização, como as classes ReentrantLock e as interfaces Condition. A escolha do mecanismo adequado depende das necessidades específicas da aplicação.

# Atividade Pratica 02:
* Execute o programa [MeuDadoMonitorJava.java](./MeuDadoMonitorJava.java), de maneira que gere uma log da execução;
* Realize uma comparação desta execução com as duas execuções anteriores;

# Sincronização usando Eventos
* Acesse o conteúdo [aqui](../eventos/eventos.md)
