package lk.ijse.helloshoe.util;

import lk.ijse.helloshoe.dto.*;
import lk.ijse.helloshoe.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenerateID generateID;

    private List<ImgHolderDTO> imgHolderDTOList = new ArrayList<>();

    //    Customer
    public Customer toCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);

    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);

    }

    public List<Customer> toCustomerList(List<CustomerDTO> customerDTOList) {
        return modelMapper.map(customerDTOList, new TypeToken<ArrayList<Customer>>() {
        }.getType());

    }

    public List<CustomerDTO> toCustomerDTOList(List<Customer> customerList) {
        return modelMapper.map(customerList, new TypeToken<ArrayList<CustomerDTO>>() {
        }.getType());

    }

    //    Employee
    public Employee toEmployee(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);

    }

    public EmployeeDTO toEmployeeDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);

    }

    public List<Employee> toEmployeeList(List<EmployeeDTO> employeeDTOList) {
        return modelMapper.map(employeeDTOList, new TypeToken<ArrayList<Employee>>() {
        }.getType());

    }

    public List<EmployeeDTO> toEmployeeDTOList(List<Employee> employeeList) {
        return modelMapper.map(employeeList, new TypeToken<ArrayList<EmployeeDTO>>() {
        }.getType());

    }

    //    Supplier
    public Supplier toSupplier(SupplierDTO supplierDTO) {
        return modelMapper.map(supplierDTO, Supplier.class);

    }

    public SupplierDTO toSupplierDTO(Supplier supplier) {
        return modelMapper.map(supplier, SupplierDTO.class);

    }

    public List<Supplier> toSupplierList(List<SupplierDTO> supplierDTOList) {
        return modelMapper.map(supplierDTOList, new TypeToken<ArrayList<Supplier>>() {
        }.getType());

    }

    public List<SupplierDTO> toSupplierDTOList(List<Supplier> supplierList) {
        return modelMapper.map(supplierList, new TypeToken<ArrayList<SupplierDTO>>() {
        }.getType());

    }

    //    Item
    public Item toItem(ItemDTO itemDTO) {
        Item item = modelMapper.map(itemDTO, Item.class);
        createImageIds(itemDTO.getItemImageDTOList());

        Supplier supplier = new Supplier();
        String supplierId = itemDTO.getSupplierDTO().getSupplierId();
        supplier.setSupplierId(supplierId);

        item.setSupplier(supplier);
        List<Stock> stockList = new ArrayList<>();

        for (StockDTO stockDTO : itemDTO.getStockList()) {
            Stock stock = new Stock(
                    stockDTO.getSize(),
                    stockDTO.getQty(),
                    stockDTO.getMaxQty(),
                    stockDTO.getColour(),
                    stockDTO.getStatus(),
                    item,
                    getItemImage(stockDTO.getItemImgId())
            );

            stockList.add(stock);
        }

        item.setStockList(stockList);

        return item;

    }

    public ItemDTO toItemDTO(Item item) {
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);

        itemDTO.setSupplierDTO(toSupplierDTO(item.getSupplier()));

        List<ItemImageDTO> itemImageDTOList = new ArrayList<>();

        L1:for (Stock tempStock:item.getStockList()) {
            for (int i = 0; i < itemImageDTOList.size(); i++) {
                if (itemImageDTOList.get(i).getItemImageId().equals(tempStock.getItemImage().getImgId())) {
                    continue L1;
                }
            }

            itemImageDTOList.add(
                    new ItemImageDTO(
                            tempStock.getItemImage().getImgId() ,
                            tempStock.getItemImage().getImg()
                    )
            );

        }

        itemDTO.setItemImageDTOList(itemImageDTOList);
        System.out.println(itemDTO.getItemImageDTOList().size());

        return itemDTO;

    }

//    public List<Item> toItemList(List<ItemDTO> itemDTOList) {
//        return modelMapper.map(itemDTOList, new TypeToken<ArrayList<Item>>() {
//        }.getType());
//
//    }

    public List<ItemDTO> toItemDTOList(List<Item> itemList) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item:itemList) {
            itemDTOList.add(toItemDTO(item));

        }

        return itemDTOList;

    }

    private void createImageIds(List<ItemImageDTO> itemImageDTOList) {
        for (int i = 0; i < itemImageDTOList.size(); i++) {
            String generatedId = generateID.generateUUID();
            imgHolderDTOList.add(new ImgHolderDTO(itemImageDTOList.get(i).getItemImageId() , generatedId , itemImageDTOList.get(i).getImage()));

        }

    }

    private ItemImage getItemImage(String imgId) {
        for (ImgHolderDTO imgHolderDTO : imgHolderDTOList) {
            if (imgHolderDTO.getImgId().equals(imgId)) {
                ItemImage itemImage = new ItemImage();

                itemImage.setImgId(imgHolderDTO.getGeneratedImgId());
                itemImage.setImg(imgHolderDTO.getImg());

                return itemImage;

            }

        }

        return null;

    }


}
