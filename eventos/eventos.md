# Sincronização usando Eventos
A sincronização usando eventos, complementada pelos métodos wait() e notify(), é uma ferramenta poderosa para controlar a execução de threads em Java. Essa abordagem oferece um nível mais granular de controle do que os monitores, permitindo que threads se comuniquem e sincronizem suas ações de forma mais flexível.

##### Como funciona a Sincronização com Eventos?
* **wait()**: Quando uma thread invoca o método wait() em um objeto, ela entra em estado de espera (bloqueada) até que outra thread notifique esse objeto. A thread libera o monitor do objeto enquanto espera.
* **notify()**: Ao chamar notify(), uma thread "acorda" uma única thread que está esperando nesse objeto. A thread acordada compete com outras threads para adquirir o monitor e continuar sua execução.
* **notifyAll()**: Acorda todas as threads que estão esperando nesse objeto.

Exemplo Prático: Produtor-Consumidor
```java
public class Buffer {
    private int[] buffer;
    private int count;
    private int in;
    private int out;

    public synchronized void put(int item) {
        while (count == buffer.length) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }
        // ... colocar item no buffer
        count++;
        notify();
    }

    public synchronized int get() {
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {}
        }
        // ... remover item do buffer
        count--;
        notify();
        return item;
    }
}
```

* **Produtor**: Chama put() para adicionar um item ao buffer. Se o buffer estiver cheio, ele chama wait(), liberando o monitor e esperando que um consumidor remova um item.
* **Consumidor**: Chama get() para remover um item do buffer. Se o buffer estiver vazio, ele chama wait(), liberando o monitor e esperando que um produtor adicione um item.
* 
##### Vantagens da Sincronização com Eventos:
* **Flexibilidade**: Permite criar padrões de comunicação mais complexos entre threads.
* **Controle Fino**: Permite que threads se comuniquem de forma mais específica, acordando apenas as threads que precisam ser notificadas.

##### Desafios e Considerações:
* **Complexidade**: A sincronização com eventos pode ser mais complexa de implementar e depurar do que a sincronização com monitores, especialmente em cenários mais complexos.
* **Condições de Corrida**: É fundamental garantir que as condições de espera e notificação sejam consistentes para evitar deadlocks e outras condições de corrida.
* **Eficiência**: O uso excessivo de wait() e notify() pode afetar o desempenho da aplicação.]
  
##### Quando Usar Sincronização com Eventos:
1. **Padrões de Produtores-Consumidores**:
* Cenário: Imagine um sistema de filas de mensagens, onde uma thread (produtora) adiciona mensagens à fila e outra thread (consumidora) remove as mensagens.
* Solução: 
a. A fila pode ser representada por uma estrutura de dados (por exemplo, uma lista).
b. A thread produtora, ao adicionar uma mensagem, verifica se a fila está cheia. Se estiver, ela chama wait(), liberando o monitor e esperando que um consumidor remova uma mensagem.
c. A thread consumidora, ao remover uma mensagem, verifica se a fila está vazia. Se estiver, ela chama wait(), esperando que um produtor adicione uma mensagem.
d. Quando uma thread altera o estado da fila (adicionando ou removendo um elemento), ela chama notify() para acordar a outra thread que está esperando.
```java
public class FilaMensagens {
    private List<String> mensagens = new ArrayList<>();
    private int capacidade = 10;

    public synchronized void adicionar(String mensagem) {
        while (mensagens.size() == capacidade) {
            try {
                wait();
            } catch (InterruptedException e) {
                // ...
            }
        }
        mensagens.add(mensagem);
        notify();
    }

    public synchronized String remover() {
        while (mensagens.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // ...
            }
        }
        return mensagens.remove(0);
    }
}
```

2. **Sincronização Complexa**:
* **Cenário**: Imagine um simulador de trânsito, onde vários carros se movem em uma pista. Cada carro é uma thread e precisa sincronizar sua movimentação com os outros carros para evitar colisões.
* Solução:
a. Cada carro pode ter um estado (parado, em movimento, esperando em um semáforo).
b. Quando um carro chega a um semáforo vermelho, ele chama wait(), esperando que o sinal abra.
c. Quando o sinal muda para verde, o semáforo chama notifyAll() para acordar todos os carros que estão esperando.
```java
public class Carro {
    // ... outros atributos

    public synchronized void esperarNoSemáforo() {
        while (semaforo.isVermelho()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // ...
            }
        }
    }
}
```
##### Atividade Prática 03
* Execute o programa [MeuDadoEventJava](./MeuDadoEventJava.java), de modo que gere uma log da execução;
* Realize uma comparação dos resultados desta execução com os obtidos com as execuções da Atividade Pratica 01 e 02;

[Voltar Inicio](../README.md)
