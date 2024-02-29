package maven.connectSqlEclipse;
import java.sql.*;
//FONTE: https://github.com/icei-pucminas/ti2cc

public class DAO {
    private Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "postgres";
        String password = "edson";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirPassagemAerea(PassagemAerea passagemAerea) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO passagem_aerea (codigo, nome, cidade_origem, cidade_destino) "
                    + "VALUES (" + passagemAerea.getCodigo() + ", '" + passagemAerea.getNome() + "', '"
                    + passagemAerea.getCidadeOrigem() + "', '" + passagemAerea.getCidadeDestino() + "');");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarPassagemAerea(PassagemAerea passagemAerea) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE passagem_aerea SET nome = '" + passagemAerea.getNome() + "', cidade_origem = '"
                    + passagemAerea.getCidadeOrigem() + "', cidade_destino = '" + passagemAerea.getCidadeDestino()
                    + "'" + " WHERE codigo = " + passagemAerea.getCodigo();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirPassagemAerea(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM PassagemAerea WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public PassagemAerea[] getPassagensAereas() {
        PassagemAerea[] passagensAereas = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM passagem_aerea");
            if (rs.next()) {
                rs.last();
                passagensAereas = new PassagemAerea[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    passagensAereas[i] = new PassagemAerea(rs.getInt("codigo"), rs.getString("nome"),
                            rs.getString("cidade_origem"), rs.getString("cidade_destino"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return passagensAereas;
    }

    public PassagemAerea getPassagemAereaByCodigo(int codigo) {
        PassagemAerea passagemAerea = null;

        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM passagem_aerea WHERE codigo = " + codigo);

            if (rs.next()) {
                passagemAerea = new PassagemAerea(rs.getInt("codigo"), rs.getString("nome"),
                        rs.getString("cidade_origem"), rs.getString("cidade_destino"));
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return passagemAerea;
    }
}
