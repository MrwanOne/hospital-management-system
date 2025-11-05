package com.acme.hms.dao;

import com.acme.hms.model.InventoryItem;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InventoryDao extends AbstractDao<InventoryItem> {

    private static final String BASE_SELECT = "SELECT id, item_code, name, qty, unit, min_qty, price FROM inventory";

    public List<InventoryItem> findAll() throws SQLException {
        return findMany(BASE_SELECT, ps -> { }, this::mapRow);
    }

    public Optional<InventoryItem> findByCode(String code) throws SQLException {
        return findOne(BASE_SELECT + " WHERE item_code = ?", ps -> ps.setString(1, code), this::mapRow);
    }

    public long insert(InventoryItem item) throws SQLException {
        String sql = "INSERT INTO inventory (item_code, name, qty, unit, min_qty, price) VALUES (?,?,?,?,?,?)";
        return insert(sql, ps -> {
            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getUnit());
            ps.setInt(5, item.getMinQuantity());
            ps.setBigDecimal(6, item.getPrice());
        });
    }

    public int update(InventoryItem item) throws SQLException {
        String sql = "UPDATE inventory SET name=?, qty=?, unit=?, min_qty=?, price=? WHERE id=?";
        return update(sql, ps -> {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getQuantity());
            ps.setString(3, item.getUnit());
            ps.setInt(4, item.getMinQuantity());
            ps.setBigDecimal(5, item.getPrice());
            ps.setLong(6, item.getId());
        });
    }

    private InventoryItem mapRow(ResultSet rs) throws SQLException {
        InventoryItem item = new InventoryItem();
        item.setId(rs.getLong("id"));
        item.setItemCode(rs.getString("item_code"));
        item.setName(rs.getString("name"));
        item.setQuantity(rs.getInt("qty"));
        item.setUnit(rs.getString("unit"));
        item.setMinQuantity(rs.getInt("min_qty"));
        item.setPrice(rs.getBigDecimal("price"));
        return item;
    }
}
