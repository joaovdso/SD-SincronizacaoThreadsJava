[Voltar Inicio](../README.md)

## Sumário
* [Analisando o Problema de Sincronização](#analisando-o-problema-de-sincronização)
* [Entendendo o Problema](#entendendo-o-problema)
* [Por que a Sincronização é Importante?](#por-que-a-sincronização-é-importante)
* [Atividade Prática 01](#atividade-prática-01)
* [Sincronização utilizando Monitores](#sincronização-utilizando-monitores)

### Analisando o Problema de Sincronização
O problema principal o código [MeuDadoThreadJava](./MeuDadoThreadsJava.java) é a ausência de sincronização ao acessar o recurso compartilhado que é a variavel "Dado".

[voltar sumário](#sumário)

## Entendendo o Problema
* **Recurso Compartilhado**: A variável Dado dentro da classe MeuDadoThreads é um recurso compartilhado por ambas as threads, Produtora e Consumidora.
* **Acesso Concorrente**: Ambas as threads acessam e modificam esse recurso de forma concorrente, sem nenhuma proteção.
* **Condição de Corrida**: A falta de sincronização pode levar a uma condição de corrida. Isso significa que o valor final de Dado pode ser imprevisível e depender da ordem em que as instruções das threads são executadas pelo processador.
* **Exemplo de cenário indesejado**: Imagine que o Produtor está armazenando o valor 10 e o Consumidor, ao mesmo tempo, está tentando carregar o valor. Se o processador interromper a execução do Produtor no meio da atribuição, o Consumidor pode ler um valor inconsistente ou até mesmo um valor antigo.
* **Introduzindo a Sincronização**: Para resolver esse problema, é necessário garantir que apenas uma thread acesse o recurso compartilhado por vez. Podemos utilizar a palavra-chave synchronized em Java para isso.

```java
class MeuDadoThreads {
    private int Dado;

    public synchronized void armazenar(int Dado) {
        this.Dado = Dado;
    }

    public synchronized int carregar() {
        return this.Dado;
    }
}
```
* Ao adicionar synchronized aos métodos armazenar e carregar, estamos associando um monitor ao objeto MeuDadoThreads. Quando uma thread entra em um desses métodos, ela adquire o monitor, impedindo que outra thread entre no mesmo método até que a primeira termine. Isso garante a exclusão mútua e evita a condição de corrida. Com essa alteração, o código ficará seguro e consistente, garantindo que o valor de Dado seja sempre atualizado corretamente.

[voltar sumário](#sumário)

## Por que a Sincronização é Importante?
* **Integridade dos dados**: Assegura que os dados não sejam corrompidos por acessos concorrentes.
* **Consistência**: Garante que as operações sejam realizadas em uma ordem correta e previsível.
* **Prevenção de condições de corrida**: Evita resultados imprevisíveis e não determinísticos.

[voltar sumário](#sumário)

# Atividade Prática 01
* Execute o programa [MeuDadoThreads](./MeuDadoThreadsJava.java), de modo que ele gere uma log da execução;
* Execute novamente o mesmo programa, de modo que gere uma nova log de executação sem sobrescrever a anterior;
* Compare as duas execuções e verifique se o resultado foi diferente.

[voltar sumário](#sumário)

# Sincronização utilizando Monitores
* Acesse o conteúdo [aqui](../monitor/monitor.md)

[voltar sumário](#sumário)
