Task Optimizer: Otimizador Dinâmico de Tarefas Diárias

O **Task Optimizer** é um projeto desenvolvido para otimizar a produtividade diária de um usuário,
fornecendo uma ordem de execução de tarefas que visa reduzir o esforço ou o tempo total gasto. 
O projeto enfatiza a priorização estratégica e a reatividade a eventos externos.

Requisitos do Projeto

### 1. Cadastro de Tarefas (Input)
O usuário deve cadastrar um conjunto de **7 tarefas** diárias. Cada tarefa deve possuir as seguintes características de entrada:

* **Duração Estimada:** O tempo previsto para a conclusão da tarefa.
* **Prioridade Base (Usuário):** Definida pelo usuário (e.g., Alta, Média, Baixa). Esta é a prioridade inicial da tarefa.

### 2. Algoritmo de Otimização (O Core da Lógica)

O sistema deve processar as tarefas cadastradas e devolver uma lista reordenada, que é considerada a sequência ideal de execução.

A lógica de ordenação é definida pela prioridade do usuário, seguida pela duração.

**Regras de Ordenação:**

1.  **Prioridade do Usuário:** É o critério principal. Tarefas com maior prioridade vêm primeiro.
2.  **Duração (Tempo):** Serve como critério de desempate. Dentro do mesmo nível de prioridade, a regra é **dar preferência à tarefa que levar mais tempo** (ordem decrescente de duração).

### 3. Reatividade e Prioridade Dinâmica (Diferencial)

A lista otimizada deve ser **recalculada automaticamente** quando um "evento externo" simulado ocorre.

* **Função:** Um evento externo (como uma reunião cancelada, um atraso de equipe, ou um imprevisto) deve **alterar a prioridade interna/ajustada** de uma ou mais tarefas.
* **Impacto:** Esta nova prioridade ajustada deve ser incorporada ao algoritmo de otimização, gerando uma nova ordem ideal de execução.

---


## 🛠️ Tecnologias e Implementação (Visão Geral)

Este projeto será desenvolvido em Java/Spring Boot e utilizará DTOs e serviços dedicados para garantir a separação de responsabilidades. A prioridade será modelada usando um Enum para garantir a segurança e validade dos dados em todo o sistema.

* **Linguagem:** Java
* **Framework:** Spring Boot
* **Padrões:** DTO, Service Layer, Padrão de Mapeamento de Entidades.
