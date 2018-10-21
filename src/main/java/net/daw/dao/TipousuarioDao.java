package net.daw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.daw.bean.TipousuarioBean;

public class TipousuarioDao {

    Connection oConnection;
    String ob = "tipoUsuario";

    public TipousuarioDao(Connection oConnection) {
        super();
        this.oConnection = oConnection;
    }

    public TipousuarioBean get(int id) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id=?";
        TipousuarioBean oTipousuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oTipousuarioBean = new TipousuarioBean();
                oTipousuarioBean.setId(oResultSet.getInt("id"));
                oTipousuarioBean.setDesc(oResultSet.getString("desc"));
            } else {
                oTipousuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de tipousuario", e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return oTipousuarioBean;

    }

    public TipousuarioBean remove(int id) throws Exception {

        String strSQL = "DELETE FROM " + ob + " WHERE id=?";
        TipousuarioBean oTipousuarioBean = new TipousuarioBean();;
        PreparedStatement oPreparedStatement = null;

        if (this.get(id) == null) {
            oTipousuarioBean.setId(id);
            oTipousuarioBean.setDesc("No existe");
        } else {
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oPreparedStatement.setInt(1, id);
                oPreparedStatement.execute();

                oTipousuarioBean.setId(id);
                oTipousuarioBean.setDesc("Eliminado con exito");

            } catch (SQLException e) {
                throw new Exception("Error en Dao remove de tipousuario", e);

            } finally {
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        }

        return oTipousuarioBean;

    }

    public TipousuarioBean getCount() throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob;
        TipousuarioBean oTipousuarioBean = null;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            //oPreparedStatement.setString(1, ob);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oTipousuarioBean = new TipousuarioBean();
                oTipousuarioBean.setId(oResultSet.getInt(1));//¿"id"?
                oTipousuarioBean.setDesc("Registros en la tabla tipousuario");
            } else {
                oTipousuarioBean = null;
            }
        } catch (SQLException e) {

            throw new Exception("Error en Dao getCount de tipousuario: " + e.getMessage(), e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return oTipousuarioBean;

    }

    public TipousuarioBean create(String desc) throws Exception {

        String strSQL = "INSERT INTO `" + ob + "` (`desc`) VALUES (?)";
        TipousuarioBean oTipousuarioBean = null;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);

            oPreparedStatement.setString(1, desc);
            int registros = oPreparedStatement.executeUpdate();
            if (registros == 1) {
                oResultSet = oPreparedStatement.getGeneratedKeys();
                oTipousuarioBean = new TipousuarioBean();
                if (oResultSet.next()) {
                    oTipousuarioBean.setId(oResultSet.getInt(1));
                    oTipousuarioBean.setDesc(desc);
                } else {
                    oTipousuarioBean.setId(404);
                    oTipousuarioBean.setDesc("Fallo en la consulta SQL");
                }

            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao create de tipousuario: " + e.getMessage(), e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }

        return oTipousuarioBean;

    }

    public TipousuarioBean update(String desc, int id) throws Exception {

        String strSQL = "UPDATE `" + ob + "` SET `desc`= ? WHERE `id`= ?";
        TipousuarioBean oTipousuarioBean = new TipousuarioBean();
        PreparedStatement oPreparedStatement = null;

        if (this.get(id) == null) {
            oTipousuarioBean.setId(id);
            oTipousuarioBean.setDesc("No existe");
        } else {
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oPreparedStatement.setString(1, desc);
                oPreparedStatement.setInt(2, id);
                int registros = oPreparedStatement.executeUpdate();
                if (registros == 1) {
                    oTipousuarioBean = this.get(id);
                } else {
                    oTipousuarioBean.setId(404);
                    oTipousuarioBean.setDesc("Fallo en la consulta SQL");
                }
            } catch (SQLException e) {
                throw new Exception("Error en Dao update de tipousuario: " + e.getMessage(), e);
            } finally {
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        }

        return oTipousuarioBean;

    }
}
