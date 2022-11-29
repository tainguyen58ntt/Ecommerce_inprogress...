package com.example.bigProduct.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.bigProduct.domain.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.bigProduct.domain.Category;
import com.example.bigProduct.model.CategoryDTO;
import com.example.bigProduct.model.ProductDTO;
import com.example.bigProduct.service.CategoryService;
import com.example.bigProduct.service.ProductService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("categories")
    public List<CategoryDTO> getCategories() {
        return categoryService.findAll().stream().map(item -> {
            CategoryDTO dto = new CategoryDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }

    @GetMapping("/add")
    public String add(Model model) {


        model.addAttribute("product", new ProductDTO());
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
        Optional<Category> opt = categoryService.findById(productId);
        CategoryDTO dto = new CategoryDTO();
        if (opt.isPresent()) {
            Category entity = opt.get();
            BeanUtils.copyProperties(entity, dto);  // copy tu entity sang dto
            dto.setIsEdit(true);
            model.addAttribute("category", dto);
            return new ModelAndView("admin/products/addOrEdit", model);
        }
        model.addAttribute("message", "Category is not exists");
        return new ModelAndView("forward:/admin/products", model);
    }

    @GetMapping("delete/{productId}")
    public ModelAndView delete(@PathVariable("productId") Long productId, ModelMap model) {

        categoryService.deleteById(productId);
        model.addAttribute("message", "category is deleted");
        return new ModelAndView("forward:/admin/products/search", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/products/addOrEdit");
        }
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        entity.setCategory(category);
        if(!dto.getImageFile().isEmpty()){
            // do sth later
        }
        productService.save(entity);
        model.addAttribute("message", "Product is saved !");
        return new ModelAndView("forward:/admin/products", model);

    }

    @RequestMapping("")
    public String list(ModelMap model) {
        List<Product> list = productService.findAll();
        model.addAttribute("products", list);
        return "admin/products/search";
    }

    @GetMapping("search")
    public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
        List<Category> list = null;
        if (StringUtils.hasText(name)) {
            list = categoryService.findByNameContaining(name);
        } else {
            list = categoryService.findAll();
        }

        model.addAttribute("products", list);

        return "/admin/products/search";
    }

}
