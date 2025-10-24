# 🧩 Análise de Sincronização entre Threads
### Disciplina: Programação Concorrente  
### Autor: *[Seu Nome Aqui]*  
### Data: *[dd/mm/aaaa]*  

---

## 🧠 Introdução

Este relatório apresenta a **análise das Atividades Práticas 01, 02 e 03**, cujo objetivo foi compreender e aplicar os mecanismos de **sincronização entre threads** em Java, explorando como o controle (ou a ausência dele) influencia o comportamento do programa, a ordem de execução e a integridade dos dados.

As atividades abordam progressivamente:
1. **Atividade 01:** Execução sem sincronização.  
2. **Atividade 02:** Sincronização com bloqueio de região crítica.  
3. **Atividade 03:** Comunicação via eventos com `wait()` e `notify()`.

Cada atividade foi executada e analisada individualmente, com observação de saídas, comportamento das threads e conclusões sobre os efeitos da sincronização.

---

## ⚙️ Atividade 01 – Execução sem Sincronização

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
📸 *Espaço para inserir print da saída:*  
![Print Atividade 1](imagens/atv1.png)

---

### **Análise Técnica**

- As threads **executam simultaneamente** e acessam a variável `dado` sem coordenação.  
- O **consumidor pode ler valores antigos** ou valores ainda não atualizados pelo produtor.  
- A **ordem de execução é imprevisível**, variando a cada execução.  
- Há **perda de integridade nos dados**, pois leituras e escritas ocorrem ao mesmo tempo.

### **Opinião Pessoal**

Essa atividade mostra claramente o problema da falta de sincronização. É interessante perceber que, mesmo em programas simples, o comportamento se torna caótico. As threads parecem "competir" pela CPU, e os resultados são inconsistentes. Foi um bom ponto de partida para entender a necessidade da coordenação.

---

## 🔒 Atividade 02 – Sincronização com Região Crítica

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
📸 *Espaço para inserir print da saída:*  
![Print Atividade 2](imagens/atv2.png)

---

### **Análise Técnica**

- O uso do modificador `synchronized` **impede acessos concorrentes simultâneos** aos métodos `armazenar()` e `carregar()`.  
- A execução se torna **mais previsível**, mas ainda **não há garantia de alternância** entre produtor e consumidor.  
- Pode ocorrer do consumidor ler o mesmo valor mais de uma vez ou o produtor sobrescrever antes de o consumidor ler.

### **Opinião Pessoal**

Percebe-se uma grande melhora na integridade dos dados, pois não há mais interferência direta de uma thread sobre a outra. Contudo, ainda faltava um mecanismo de **coordenação temporal** — algo que garanta que o produtor e o consumidor alternem suas ações, o que será resolvido na próxima atividade.

---

## 🔁 Atividade 03 – Comunicação via Eventos (wait/notify)

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
📸 *Espaço para inserir prints da saída (início, meio e fim da execução):*  
![Print Atividade 3 - 1](imagens/atv3-1.png)  
![Print Atividade 3 - 2](imagens/atv3-2.png)

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

## 📊 Comparativo das Atividades

| Atividade | Mecanismo de Sincronização | Ordem de Execução | Integridade dos Dados | Observações |
|------------|----------------------------|-------------------|------------------------|--------------|
| **01** | Nenhum | Caótica | Comprometida | Condições de corrida e valores aleatórios. |
| **02** | `synchronized` | Parcialmente ordenada | Boa | Evita conflito, mas não coordena alternância. |
| **03** | `wait()` / `notify()` | Ordenada | Total | Comunicação perfeita entre produtor e consumidor. |

---

## 🧩 Conclusão Geral

As três atividades permitiram compreender, de forma progressiva, a importância da **sincronização em sistemas concorrentes**.  
A ausência de controle (Atv 1) leva à perda total de integridade; o controle parcial (`synchronized`, Atv 2) melhora a consistência, mas não garante alternância; e a coordenação com eventos (`wait()`/`notify()`, Atv 3) proporciona **execuções ordenadas, seguras e realistas**.

A evolução entre as atividades mostra como pequenos ajustes na forma de sincronização podem alterar completamente o comportamento de programas com múltiplas threads.

---

## 📚 Referências

- DEITEL, Paul; DEITEL, Harvey. *Java: Como Programar*, 10ª ed. Pearson, 2016.  
- DOWNEY, Allen B. *Think Java: How to Think Like a Computer Scientist*. O’Reilly, 2019.  
- ORACLE. *Java Platform Standard Edition Documentation*. Disponível em: [https://docs.oracle.com/javase/](https://docs.oracle.com/javase/)
