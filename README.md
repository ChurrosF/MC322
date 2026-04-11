# Jogo de RPG de cartas

Esse é um jogo feito inteiramente em Java como parte da disciplina MC322 da Unicamp. Sua parte gráfica é inteiramente feita em terminal, usando como inspiração jogos feitos em ASCII ART, além de outras inspirações em jogos de carta e RPG.

# Dependências

+ JDK (Java Development Kit), que é o que contém o compilador javac para criar os programas.

# Como compilar esse projeto?
Na pasta do repositório e cole ou escreva:

```sh
gradle wrapper

chmod +x gradlew

./gradlew build

./gradlew run
```

# Como Jogar?

O jogo é baseado em turnos, onde você deve derrotar o formidável Grande Rato, que anda colocando o terror nos vilarejos perto do castelo do Rei. O personagem possui 3 atributos principais: vida, energia e escudo. Se sua vida chegar a 0 você perde! O escudo cumpre a função de te proteger, ele reduz todo o dano inimigo em sua quantidade. A energia representa o seu limite de cartas usáveis por turno, ou seja, ao zerar a energia, você não consegue mais usar cartas! Assim, você deve controlar esse 3 recursos afim de derrotar o inimigo. Boa sorte!

+ Vocẽ recebe um deck com 20 cartas, recebendo 5 cartas cada turno para sua hand, que ao final de sua jogada são descartadas.
+ Escolha uma carta dos indices de 1 até 5 por meio do teclado. Existem várias cartas no jogo cada uma com sua função! No final desse README existe uma descrição do que cada um dos indicadores das cartas representa.
+ Sempre que escolher uma carta que ataque um inimigo, escolha qual inimigo atacar, aperte 1 ou 2 no teclado.
+ Para recuperar sua energia e acabar seu turno aperte a tecla "P" + Enter 

Patch Notes Version 0.1.4

Agora com novas cartas e múltiplos inimigos!

# Interface do Jogo


![imagem exemplo](/assets/imageREADME.png)

+ DMG: Dano causado
+ SHD: Escudo recebido
+ PSN: Quantidade de veneno infligida
+ STR: Quantidade de buff de força ganho
+ MANA: Duração do efeito Mana Overflow, que aumenta a energia máxima em 2
+ REG: Quantidade de energia recuperada


# Aviso
    JAVADOC foi implementado com ajuda de LLM

Nome: Felipe Pedral Cruz de Oliveira RA: 220826
Nome: Mateus Carioca RA: 282046
