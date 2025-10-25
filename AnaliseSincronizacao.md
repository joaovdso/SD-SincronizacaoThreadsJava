# Análise de Sincronização entre Threads
### Disciplina: Programação Concorrente  
### Autor: João Vitor da Silva Oliveira 
### Data: 30/09/2025
---

## Introdução

Este relatório apresenta a **análise das Atividades Práticas 01, 02 e 03**, cujo objetivo foi compreender e aplicar os mecanismos de **sincronização entre threads** em Java, explorando como o controle (ou a ausência dele) influencia o comportamento do programa, a ordem de execução e a integridade dos dados.

As atividades abordam progressivamente:
1. **Atividade 01:** Execução sem sincronização.  
2. **Atividade 02:** Sincronização com bloqueio de região crítica.  
3. **Atividade 03:** Comunicação via eventos com `wait()` e `notify()`.

Cada atividade foi executada e analisada individualmente, com observação de saídas, comportamento das threads e conclusões sobre os efeitos da sincronização.

---

##  Atividade 01 – Execução sem Sincronização

### **Descrição**

Nesta primeira atividade, duas threads (Produtor e Consumidor) compartilham uma variável global, **sem nenhum controle de acesso simultâneo**. Ambas executam independentemente, o que pode gerar **condições de corrida**.

### **Código-Fonte Resumido**

```java
class MeuDadoThreads {
    private int Dado;
    public void armazenar(int Dado) { this.Dado = Dado; }
    public int carregar() { return this.Dado; }
}
```

### **Execução**
<img width="1920" height="1080" alt="Print log1" src="https://github.com/user-attachments/assets/186267e8-b976-4325-8e33-a3ece539c9d1" />

<img width="1920" height="1080" alt="Print log2" src="https://github.com/user-attachments/assets/7fb92b19-4914-43da-8f8e-0748e7529c7f" />


---

### **Análise Técnica**

- As threads **executam simultaneamente** e acessam a variável `dado` sem coordenação.  
- O **consumidor pode ler valores antigos** ou valores ainda não atualizados pelo produtor.  
- A **ordem de execução é imprevisível**, variando a cada execução.  
- Há **perda de integridade nos dados**, pois leituras e escritas ocorrem ao mesmo tempo.

### **Opinião Pessoal**

Essa atividade mostra claramente o problema da falta de sincronização. É interessante perceber que, mesmo em programas simples, o comportamento se torna caótico. As threads parecem "competir" pela CPU, e os resultados são inconsistentes. Foi um bom ponto de partida para entender a necessidade da coordenação.

---

## Atividade 02 – Sincronização com Região Crítica

### **Descrição**

Nesta segunda atividade, o mesmo problema foi revisitado, mas agora com **métodos sincronizados** (`synchronized`), garantindo que apenas **uma thread por vez** acesse a variável compartilhada.

### **Código-Fonte Resumido**

```java
class MeuDadoMonitor {
    private int Dado;
    private boolean Pronto;
    private boolean Ocupado;
    // Implementação com sincronização e controle de estados
}
```

### **Execução**
<img width="1365" height="651" alt="Print-Log2" src="https://github.com/user-attachments/assets/44a68b2b-c7f9-4c1e-a5b5-430ea65c2170" />
<img width="1366" height="686" alt="Atv2-Comparacao Log3xLog1" src="https://github.com/user-attachments/assets/262ade35-6365-442a-976c-df610a4cc30b" />
<img width="1366" height="690" alt="Atv2- ComparacaoLog3xLog2" src="https://github.com/user-attachments/assets/60e0285c-bc8b-4467-b0b8-908fb637028a" />


---

### **Análise Técnica**

- O uso do modificador `synchronized` **impede acessos concorrentes simultâneos** aos métodos `armazenar()` e `carregar()`.  
- A execução se torna **mais previsível**, mas ainda **não há garantia de alternância** entre produtor e consumidor.  
- Pode ocorrer do consumidor ler o mesmo valor mais de uma vez ou o produtor sobrescrever antes de o consumidor ler.

### **Opinião Pessoal**

Percebe-se uma grande melhora na integridade dos dados, pois não há mais interferência direta de uma thread sobre a outra. Contudo, ainda faltava um mecanismo de **coordenação temporal** — algo que garanta que o produtor e o consumidor alternem suas ações, o que será resolvido na próxima atividade.

---

## Atividade 03 – Comunicação via Eventos (wait/notify)

### **Descrição**

A terceira atividade introduziu a **comunicação cooperativa entre threads**, utilizando os métodos `wait()` e `notify()` para **sincronizar a alternância de execução** entre produtor e consumidor.  
Agora, o consumidor só consome após o produtor produzir, e vice-versa.

### **Código-Fonte Completo**

```java
class MeuDadoEvent {
    private int Dado;
    private boolean Pronto;

    public synchronized void armazenar(int Data) {
        while (Pronto)
            try { wait(); } catch (InterruptedException e) { }
        this.Dado = Data;
        Pronto = true;
        notify();
    }

    public synchronized int carregar() {
        while (!Pronto)
            try { wait(); } catch (InterruptedException e) { }
        Pronto = false;
        notify();
        return this.Dado;
    }
}
```

### **Execução**
<img width="1365" height="689" alt="Atv3-log4" src="https://github.com/user-attachments/assets/49788225-626c-4b95-a7c7-9804d5929be4" />
<img width="1366" height="690" alt="Atv3-ComparacaoLog4xLog1" src="https://github.com/user-attachments/assets/ab6cb12b-ac42-4298-a700-79d6306f5db6" />
<img width="1363" height="688" alt="Atv3-ComparacaoLog4xLog2" src="https://github.com/user-attachments/assets/819bc7c5-5bed-490b-8c35-5edfcdb4c0a3" />
<img width="1365" height="684" alt="Atv3-ComparacaoLog4xLog3" src="https://github.com/user-attachments/assets/6c95f079-7aa9-4f10-86a7-f6c06fe5dd05" />


---

### **Análise Técnica**

- O uso de `wait()` e `notify()` permitiu **sincronização cooperativa**, garantindo que:  
  - O **produtor aguarde** o consumidor consumir antes de gerar novo dado.  
  - O **consumidor aguarde** até que o produtor gere um novo valor.  
- A execução torna-se **totalmente ordenada e previsível**: os valores são produzidos e consumidos na sequência correta.  
- **Nenhum valor é perdido ou duplicado.**

### **Opinião Pessoal**

Esta versão é claramente mais eficiente e lógica. O comportamento se assemelha ao funcionamento real de buffers ou filas entre processos. A alternância natural entre produção e consumo mostra o poder dos métodos de comunicação em Java. Foi a implementação mais interessante e satisfatória de observar.

---

## Comparativo das Atividades

| Atividade | Mecanismo de Sincronização | Ordem de Execução | Integridade dos Dados | Observações |
|------------|----------------------------|-------------------|------------------------|--------------|
| **01** | Nenhum | Caótica | Comprometida | Condições de corrida e valores aleatórios. |
| **02** | `synchronized` | Parcialmente ordenada | Boa | Evita conflito, mas não coordena alternância. |
| **03** | `wait()` / `notify()` | Ordenada | Total | Comunicação perfeita entre produtor e consumidor. |

---

## Conclusão Geral
As três atividades permitiram compreender, de forma progressiva, a importância da **sincronização em sistemas concorrentes**.  
A ausência de controle (Atv 1) leva à perda total de integridade; o controle parcial (`synchronized`, Atv 2) melhora a consistência, mas não garante alternância; e a coordenação com eventos (`wait()`/`notify()`, Atv 3) proporciona **execuções ordenadas, seguras e realistas**.

A evolução entre as atividades mostra como pequenos ajustes na forma de sincronização podem alterar completamente o comportamento de programas com múltiplas threads.

---

