# Jogo de RPG de cartas

Esse é um jogo feito inteiramente em Java como parte da disciplina MC322 da Unicamp. Sua parte gráfica é inteiramente feita em terminal, usando como inspiração jogos feitos em ASCII ART, além de outras inspirações em jogos de carta e RPG.

# Como compilar esse projeto?

javac -d bin $(find src -name "*.java")

java -cp bin App

# Como Jogar?

O jogo em si é bem simples, com apenas 3 ações que o player pode fazer:

- Usar carta de ataque (Pressionar 1 + Enter)
- Usar carta de escudo (Pressionar 2 + Enter)
- Passar o turno, voltando a ter 3 de energia (Pressionar 3 + Enter)

O jogo é baseado em turnos, onde você deve derrotar o formidável Grande Rato, que anda colocando o terror nos vilarejos perto do castelo do Rei. O personagem possui 3 atributos principais: vida, energia e escudo. Se sua vida chegar a 0 você perde! O escudo cumpre a função de te proteger, ele reduz todo o dano inimigo em sua quantidade. A energia representa o seu limite de cartas usáveis por turno, ou seja, ao zerar a energia, você não consegue mais usar cartas! Assim, você deve controlar esse 3 recursos afim de derrotar o inimigo. Boa sorte!


# Interface do Jogo


![imagem exemplo](image.png)

