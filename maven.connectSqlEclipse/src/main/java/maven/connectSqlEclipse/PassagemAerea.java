package maven.connectSqlEclipse;
//FONTE: https://github.com/icei-pucminas/ti2cc


public class PassagemAerea {
    private int codigo;
    private String nome;
    private String cidadeOrigem;
    private String cidadeDestino;

    public PassagemAerea() {
        this.codigo = -1;
        this.nome = "";
        this.cidadeOrigem = "";
        this.cidadeDestino = "";
    }

    public PassagemAerea(int codigo, String nome, String cidadeOrigem, String cidadeDestino) {
        this.codigo = codigo;
        this.nome = nome;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    @Override
    public String toString() {
        return "PassagemAerea [codigo=" + codigo + ", nome=" + nome + ", cidadeOrigem=" + cidadeOrigem + ", cidadeDestino=" + cidadeDestino + "]";
    }
}

