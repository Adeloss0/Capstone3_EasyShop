package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.HashMap;
import java.util.List;

// add the annotations to make this a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/categories
@RequestMapping("/categories")
// add annotation to allow cross site origin requests--COMPLETE
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;



    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    public CategoriesController(CategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }

    //     add the appropriate annotation for a get action
    @GetMapping
    public List<Category> getAllCate()
    {
        // find and return all categories--COMPLETE
        return categoryDao.getAllCategories();
    }


    // add the appropriate annotation for a get action
    @GetMapping("/{categoryId}")
    public Category getCategoriesById(@PathVariable int categoryId) {
        System.out.println("Category id: " + categoryId);
        // get the category by id--COMPLETE
        return categoryDao.getById(categoryId);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        return productDao.listByCategoryId(categoryId);
    }



    // add annotation to call this method for a POST action--COMPLETE
    @PostMapping()
    // add annotation to ensure that only an ADMIN can call this function
   @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        try{
            return categoryDao.create(category);
        }catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an ERROR!");
        }
    }




    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("{id}")
    // add annotation to ensure that only an ADMIN can call this function--COMPLETE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HashMap<String, String> updateCategory(@PathVariable int id, @RequestBody Category category){
        categoryDao.update(id, category);

        HashMap<String, String> response = new HashMap<>();

        response.put("Status", "Successful");
        response.put("Message", "Category Updated Successfully");

        return response;

//        try{
//            categoryDao.update(id, category);
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"There was an ERROR!");
//        }
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    @DeleteMapping("{id}")
    // add annotation to ensure that only an ADMIN can call this function--COMPLETE
  //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public HashMap<String, String> deleteCategory(@PathVariable int id){
        categoryDao.delete(id);
        HashMap<String, String> response = new HashMap<>();
        response.put("Status", "Successful");
        response.put("Message", "Category deleted");

        return response;





    }
}












