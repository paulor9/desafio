package com.pra.desafio.dao.categories.jdbc;



import com.pra.desafio.dao.categories.CategoriesDAO;
import com.pra.desafio.dto.CategoriesDTO;
import com.pra.desafio.exception.CategoryNotFoundException;
import com.pra.desafio.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class CategoriesDAOImp implements CategoriesDAO {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesDAOImp.class);
    private static final String QUERY_ALL_CATEGORY= "SELECT * FROM desafio.categories ";
    private static final String QUERY_CATEGORY_BY_ID= "SELECT * FROM desafio.categories WHERE category_id = ? ";
    private static final String QUERY_CATEGORY_BY_TYPE= "SELECT * FROM desafio.categories WHERE type = ? ";
    private static final String QUERY_INSERT_CATEGORY = "INSERT INTO `desafio`.`categories`( `name`, `type`) values (?, ?)";
    private static final String QUERY_UPDATE_CATEGORY = "UPDATE `desafio`.`categories` SET `name` = ?, `type` =  ? WHERE `category_id` = ?";
    private static final String QUERY_DELETE_CATEGORY= "DELETE FROM `desafio`.`categories`  WHERE `category_id` = ?";

    private final JdbcTemplate jdbcTemplate;

    public CategoriesDAOImp( JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CategoriesDTO>listAllCategories(){
      return jdbcTemplate.query(QUERY_ALL_CATEGORY ,new CategoriesRowMapper());
   }

    @Transactional
    public CategoriesDTO insertCategory( CategoriesDTO cat) throws CategoryNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);
            ps.setString (1, cat.getName());
            ps.setString (2, cat.getType());
            return ps;
        };
        logger.debug("QUERY_INSERT_CATEGORY  {} ", (QUERY_INSERT_CATEGORY));
        logger.debug("Name {}", cat.getName());
        logger.debug("Type {}", cat.getType());


        KeyHolder keyHolder = new GeneratedKeyHolder();
        var r = jdbcTemplate.update(psc,keyHolder);
        if ( ( r > 0  ) && (! keyHolder.getKeyList().isEmpty())) {
            Number i = keyHolder.getKey();
            if ( i != null )
                cat.setCategoryId(i.intValue());

        }
        return cat;
    }

    @Transactional
    public CategoriesDTO updateCategory(CategoriesDTO cat) throws CategoryNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE_CATEGORY , Statement.RETURN_GENERATED_KEYS);
            ps.setString (1, cat.getName());
            ps.setString (2, cat.getType());
            ps.setInt (3, cat.getCategoryId());
            return ps;
        };
        logger.debug("QUERY_UPDATE_CATEGORY  {} ", (QUERY_UPDATE_CATEGORY));
        logger.debug("Name {}", cat.getName());
        logger.debug("Type {}", cat.getType());
        logger.debug("id  {}", cat.getCategoryId());
         jdbcTemplate.update(psc);
        return cat;
    }

    @Transactional
    public void  deleteCategory(int id ) throws CategoryNotFoundException {
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_DELETE_CATEGORY);
            ps.setInt (1, id);
            return ps;
        };
        logger.debug("QUERY_DELETE_CATEGORY  {} ", (QUERY_DELETE_CATEGORY));
        logger.debug("id {}", id);
        jdbcTemplate.update(psc);
    }

    public CategoriesDTO  getCategoryByID(int id) throws CategoryNotFoundException {
        CategoriesDTO cat ;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps = conn.prepareStatement(QUERY_CATEGORY_BY_ID);
            ps.setInt(1, id);
            return ps;
        };
        logger.debug("query {} ", (QUERY_CATEGORY_BY_ID));
        logger.debug("id {}", id);
        List<CategoriesDTO> usrList = jdbcTemplate.query(psc, new CategoriesRowMapper());
        if (  (!usrList.isEmpty()) ){
            cat = usrList.get(0);
        }else{
            logger.debug("Category ID {} not found",  id );
            throw  new UserNotFoundException (String.format("Category ID %s not found",  id ));
        }
        return cat;
    }

    public CategoriesDTO getCategoryByType(String type)throws CategoryNotFoundException {
        CategoriesDTO usr;
        PreparedStatementCreator psc = conn -> {
            PreparedStatement ps;
            ps = conn.prepareStatement(QUERY_CATEGORY_BY_TYPE);
            ps.setString(1, type);
            return ps;
        };
        logger.debug("query {} ", (QUERY_CATEGORY_BY_TYPE));
        logger.debug("type {}", type);

        List<CategoriesDTO> catList = jdbcTemplate.query(psc, new CategoriesRowMapper());
        if (  (!catList.isEmpty()) ){
            usr = catList.get(0);
        }else{
            logger.debug("User with email  {} not found",  type);
            throw  new CategoryNotFoundException(String.format("Category with email  %s not found", type));
        }
        return usr;
    }



}
