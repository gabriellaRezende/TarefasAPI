# Documentação do Projeto TarefasAPI

## **Visão Geral**

O projeto **TarefasAPI** é uma API desenvolvida em Kotlin usando o framework Ktor. Ele oferece funcionalidades para gerenciar projetos e tarefas associadas, utilizando o banco de dados H2 como solução de armazenamento local.

O repositório possui duas branches principais:

1. **main**: Versão estável do projeto utilizando o banco de dados H2.
2. **database_Firebase**: Uma tentativa de migrar para o Firebase Realtime Database, que não foi concluída com sucesso. Portanto, a branch principal e funcional é a `main`.

---

## **Configuração do Ambiente**

### **Requisitos**

1. **Java Development Kit (JDK)**:
   * Versão: 17 ou superior.
2. **Kotlin**:
   * Versão: 2.0.21.
3. **Gradle**:
   * Versão 7.0 ou superior.

---

## **Configuração do Banco de Dados H2**

O projeto utiliza o banco de dados H2 para armazenar informações sobre projetos e tarefas. As tabelas são criadas automaticamente ao iniciar o projeto.

Para acessar a interface do banco de dados H2, ele estará disponível na porta `8082`. Acesse o console pelo seguinte link:

`http://localhost:8082`

Use as seguintes credenciais para se conectar:

* **Driver Class**: org.h2.Driver
* **JDBC URL**: jdbc:h2:./data/tarefasDB
* **User Name**: sa
* **Password**: (deixe em branco)

---

## **Execução do Projeto**

### **Passo 1: Clonar o Repositório**

`git clone <URL_DO_REPOSITORIO> cd TarefasAPI`

Por padrão, a branch `main` será utilizada. Se desejar explorar a tentativa de migração para o Firebase, utilize o comando:

`git checkout database_Firebase`

### **Passo 2: Instalar Dependências**

Execute o comando:

`gradle build`

### **Passo 3: Iniciar a API**

Execute o seguinte comando:

`./gradlew run`

A API será iniciada em: [http://localhost:8080](http://localhost:8080)

### **Passo 4: Testar os Endpoints no Postman**

O Postman foi utilizado para testar os endpoints da API, permitindo a validação de cada funcionalidade. Caso prefira, também é possível utilizar comandos `curl` para realizar testes. Seguem exemplos:

**Exemplo de Teste com** `curl`**:**

\`# Criar Projeto curl -X POST [http://localhost:8080/projetos](http://localhost:8080/projetos) \
-H "Content-Type: application/json" \
-d '{ "nome": "Projeto Exemplo", "descricao": "Uma descrição", "dataDeInicio": "2025-01-01", "dataDeFim": "2025-12-31" }'

## **Endpoints da API**

### **Projetos**

* **Criar Projeto (POST** `/projetos` )
  * **Body:**

`{ "nome": "Projeto Exemplo", `

`"descricao": "Uma descrição", `

`"dataDeInicio": "2025-01-01", `

`"dataDeFim": "2025-12-31" `

`}`

* **Listar Projetos (GET** `/projetos`)
* **Buscar Projeto por ID (GET** `/projetos/{id}`)
* **Atualizar Projeto (PUT** `/projetos/{id}`)
  * **Body:**

`{ "nome": "Projeto Atualizado", `

`"descricao": "Nova descrição", `

`"dataDeInicio": "2025-01-01", `

`"dataDeFim": "2025-12-31" `

`}`

* **Excluir Projeto (DELETE** `/projetos/{id}`)

### **Tarefas**

* **Criar Tarefa (POST** `/tarefas`)
  * **Body:**

`{ "projetoId": 1, `

`"titulo": "Finalizar API", `

`"descricao": "Implementar endpoints", `

`"prioridade": 1, `

`"status": "PENDENTE", `

`"dataDeConclusao": "2025-06-01" `

`}`

* **Listar Tarefas (GET** `/tarefas`)
* **Buscar Tarefa por ID (GET** `/tarefas/{id}`)
* **Atualizar Tarefa (PUT** `/tarefas/{id}`)
  * **Body:**

`{ "titulo": "Atualizar API", `

`"descricao": "Melhorar endpoints", `

`"prioridade": 2, `

`"status": "CONCLUIDO", `

`"dataDeConclusao": "2025-06-15" `

`}`

* **Excluir Tarefa (DELETE** `/tarefas/{id}`)

## **Notas Importantes**

1. A branch `main` utiliza o banco de dados H2 e está totalmente funcional.
2. A branch `database_Firebase` contém uma tentativa de migração para o Firebase, mas não está completa nem funcional.
3. As tabelas são criadas automaticamente ao iniciar o projeto na branch `main`.
4. Para explorar a branch `database_Firebase`, será necessário seguir etapas adicionais descritas nessa branch.
5. O Postman foi usado para validar os endpoints, mas usuários podem testar os mesmos com `curl` ou outras ferramentas semelhantes.
6. O console H2 pode ser acessado em [http://localhost:8082](http://localhost:8082) com as credenciais especificadas na seção de configuração.
