FROM jelastic/maven:3.8.6-openjdk-20.ea-b24

COPY ./ /workdir

WORKDIR /workdir

CMD ["mvn", "spring-boot:run"]