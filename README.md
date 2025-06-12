# 💸 Projeto A3 – Ciência de Dados – Detecção de Padrões Suspeitos em Transações PIX

## 🎯 Proposta do Projeto

Este projeto tem como objetivo detectar **comportamentos suspeitos relacionados a golpes financeiros praticados via Pix**, com base nos cenários descritos pelo **Banco Central do Brasil**, como:

- **Golpe do Falso Funcionário**: onde a vítima transfere valores para contas de terceiros acreditando estar lidando com o banco ou autoridade legítima;
- **Golpe do Perfil Falso ou Produto Inexistente**: onde a vítima realiza uma transferência e o golpista desaparece após o recebimento.

O sistema foi desenvolvido com base na base de dados simulada PaySim1, e utiliza **estruturas de dados implementadas manualmente** para detectar padrões de risco em contas e transações.

Fonte: [Banco Central – Golpes Financeiros](https://www.bcb.gov.br/meubc/faqs/s/golpes)

---

## 🧠 O que fizemos

- Desenvolvemos uma aplicação em **Java**, conectada a um banco de dados **MySQL**, capaz de processar milhares de transações Pix simuladas.
- Utilizamos estruturas de dados desenvolvidas do zero (listas encadeadas e árvore binária) para organizar, buscar e classificar os dados.
- Implementamos regras que detectam:
  - **Contas zumbis**: contas que fazem apenas 1 transação de valor elevado;
  - **Contas com alto volume financeiro**: possíveis hubs de golpe ou lavagem;
  - **Fraudes rotuladas**: validação cruzada com a coluna `isFraud` da base de dados;
- Geramos arquivos `.csv` para cada análise e disponibilizamos os resultados com clareza.

---

## ⚙️ Tecnologias Utilizadas

- **Linguagem:** Java 17
- **Banco de Dados:** MySQL
- **Conexão com o BD:** JDBC
- **Dataset:** [PaySim1 – Kaggle](https://www.kaggle.com/datasets/ntnu-testimon/paysim1)
- **Ferramentas:** Git, GitHub, SCP, SSH

---

## 🧱 Estrutura e Arquitetura da Solução

A aplicação segue um fluxo simples e eficiente:

1. **Importação dos dados** do MySQL (`transactions`).
2. **Armazenamento nas estruturas personalizadas**, como listas de transações e árvore de contas.
3. **Análise de padrões suspeitos** usando regras baseadas em comportamento.
4. **Exportação dos resultados** em arquivos `.csv` para visualização e análise.

### Estruturas de Dados Implementadas:

✔️ **Listas Encadeadas**  
- Usadas para armazenar transações separadas por tipo (`TRANSFER`, `CASH_OUT`, etc.).
- Permitem varreduras e análises sequenciais, como filtragem por tipo ou valor.

✔️ **Árvore Binária de Busca (ABB)**  
- Utilizada para armazenar contas com base em valor movimentado.
- Permite extração eficiente das **top 10 contas mais ativas**.

✔️ **Contadores customizados**  
- Estrutura auxiliar para contar e comparar tipos de transações suspeitas ou fraudulentas.

> Todas as estruturas foram criadas manualmente conforme exigido na disciplina, sem uso de bibliotecas externas.

---

## 📂 Arquivos Gerados (.csv)

| Arquivo | Descrição |
|--------|-----------|
| `original_isfraud.csv` | Transações identificadas como fraudes pelo campo oficial `isFraud = 1` |
| `fraud_transactions.csv` | Subconjunto de fraudes que também estão nas listas analisadas pela aplicação |
| `zombie_accounts.csv` | Contas com apenas 1 transação e alto valor (acima do percentil 95) |
| `top_accounts.csv` | Top 10 contas que mais movimentaram valores no sistema |

---

## 🧪 Resultados e Conclusões

A análise demonstrou que é possível:
- Detectar **comportamentos atípicos de contas**, como uso único ou movimentações desproporcionais;
- Identificar **potenciais fraudes sem depender exclusivamente de `isFraud`**, reforçando a robustez analítica;
- Utilizar estruturas de dados simples para tratar **grandes volumes de dados com eficiência**.

Além disso, mesmo sem cruzamento exato entre nossas detecções e os rótulos de fraude da base, demonstramos a **eficácia de critérios comportamentais independentes** para triagem de risco.

---

## 💻 Demonstração na Nuvem

O projeto foi executado em uma **máquina virtual Ubuntu 24.04 LTS na Microsoft Azure (via Azure for Students)**, utilizando:

- SSH para acesso remoto
- SCP para envio/recebimento de arquivos
- JDK 17 instalado diretamente na VM
- Execução remota via terminal

Os arquivos `.csv` foram gerados diretamente na nuvem e transferidos para análise local.

---

## 👥 Integrantes do Grupo

- Aline Souza Rodrigues – RA: 12524229895  
- Gustavo Henrique da Silva – RA: 12523161925  
- Julia Cristina Ferreira Silva – RA: 1252413494  
- Victor Otávio Carella de Brito – RA: 12524118312  

---

## 📌 Considerações Finais

Este projeto cumpre integralmente os requisitos da atividade prática A3 da disciplina **Estrutura de Dados e Análise de Algoritmos**, incluindo:

- Uso da linguagem Java
- Conexão com banco relacional
- Implementação de estruturas dinâmicas manuais
- Geração de arquivos e relatórios

> Ao focar na detecção de padrões comportamentais, entregamos um sistema com potencial de aplicação real, que complementa os métodos tradicionais de detecção de fraudes.
