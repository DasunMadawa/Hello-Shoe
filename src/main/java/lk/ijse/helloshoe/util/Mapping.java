package lk.ijse.helloshoe.util;

import lk.ijse.helloshoe.dto.*;
import lk.ijse.helloshoe.entity.*;
import lk.ijse.helloshoe.entity.enums.Category;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lk.ijse.helloshoe.entity.enums.StockStatus;
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

//        System.out.println(item);
//        System.out.println(itemDTO);

        Supplier supplier = new Supplier();
        supplier.setSupplierId(itemDTO.getSupplierDTO().getSupplierId());

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

        L1:
        for (Stock tempStock : item.getStockList()) {
            for (int i = 0; i < itemImageDTOList.size(); i++) {
                if (itemImageDTOList.get(i).getItemImageId().equals(tempStock.getItemImage().getImgId())) {
                    continue L1;
                }
            }

            itemImageDTOList.add(
                    new ItemImageDTO(
                            tempStock.getItemImage().getImgId(),
                            tempStock.getItemImage().getImg()
                    )
            );

        }

        itemDTO.setItemImageDTOList(itemImageDTOList);
//        System.out.println(itemDTO.getItemImageDTOList().size());

        return itemDTO;

    }

    public List<Item> toItemList(List<ItemDTO> itemDTOList) {
        return modelMapper.map(itemDTOList, new TypeToken<ArrayList<Item>>() {
        }.getType());

    }

    public List<ItemDTO> toItemDTOList(List<Item> itemList) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOList.add(
                    new ItemDTO(
                            item.getICode(),
                            item.getDescription(),
                            item.getCategory(),
                            item.getPriceBuy(),
                            item.getPriceSell(),
                            null,
                            null,
                            null

                    )
            );

        }

        return itemDTOList;

    }

    public List<SaleItemHolderDTO> getSaleItems(List<Item> itemList) {
        List<SaleItemHolderDTO> saleItemHolderDTOList = new ArrayList<>();

        for (Item item : itemList) {
            SaleItemHolderDTO saleItemHolderDTO = new SaleItemHolderDTO();

            boolean isAvailable = setHoldersData(saleItemHolderDTO, item);

            if (isAvailable) {
                saleItemHolderDTOList.add(saleItemHolderDTO);

            }

        }

        return saleItemHolderDTOList;

    }

    public List<Stock> getStockList(ItemDTO itemDTO, Item item) {
        createImageIds(itemDTO.getItemImageDTOList());

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

        return stockList;


    }

    public List<Stock> toStockList(List<StockDTO> stockDTOList, Item item) {
        List<Stock> stockList = new ArrayList<>();

        List<StockDTO> tempStockDTOListQTY = new ArrayList<>();
        List<StockDTO> tempStockDTOListMaxQTY = new ArrayList<>();

        for (int i = 0; i < stockDTOList.size(); i++) {
            if (stockDTOList.get(i).getSize() == null) {
                tempStockDTOListQTY = tempStockDTOListMaxQTY;
                tempStockDTOListMaxQTY = new ArrayList<>();
                System.out.println("Iter - " + i);
                continue;
            }

            tempStockDTOListMaxQTY.add(stockDTOList.get(i));
        }

        for (int i = 0; i < tempStockDTOListQTY.size(); i++) {
            System.out.println(tempStockDTOListMaxQTY.get(i).getMaxQty());

            Stock stock = new Stock(
                    tempStockDTOListQTY.get(i).getSize(),
                    tempStockDTOListQTY.get(i).getQty(),
                    tempStockDTOListMaxQTY.get(i).getMaxQty(),
                    tempStockDTOListQTY.get(i).getColour(),
                    tempStockDTOListQTY.get(i).getStatus(),
                    item,
                    getItemImage(item, tempStockDTOListQTY.get(i).getItemImgId())

            );

            stock.setStatus(statusCalc(stock.getQty(), stock.getMaxQty()));

            stockList.add(stock);

        }

        return stockList;

    }

    public Sale toSale(SaleDTO saleDTO) {
        return modelMapper.map(saleDTO , Sale.class);

    }

    public SaleDTO toSaleDTO(Sale sale) {
        return modelMapper.map(sale, SaleDTO.class);

    }

    public List<SaleDTO> toSaleDTOList(List<Sale> saleList) {
        return modelMapper.map(saleList, new TypeToken<List<SaleDTO>>() {
        }.getType());

    }

    public Refund toRefund(RefundDTO refundDTO){
        return modelMapper.map(refundDTO , Refund.class);

    }

    public RefundDTO toRefundDTO(Refund refund){
        return modelMapper.map(refund , RefundDTO.class);

    }

    private boolean setHoldersData(SaleItemHolderDTO saleItemHolderDTO, Item item) {
        List<Colour> availableColourList = new ArrayList<>();
        List<SaleItemQtyHolderDTO> saleItemQtyHolderDTOList = new ArrayList<>();
        List<SaleItemImageHolderDTO> saleItemImageHolderDTOList = new ArrayList<>();

        for (Stock stock : item.getStockList()) {
            if (!(stock.getQty() > 0)) {
                continue;
            }

            if (!availableColourList.contains(stock.getColour())) {
                availableColourList.add(stock.getColour());
                saleItemImageHolderDTOList.add(new SaleItemImageHolderDTO(stock.getColour(), stock.getItemImage().getImg()));
            }

            saleItemQtyHolderDTOList.add(
                    new SaleItemQtyHolderDTO(
                            stock.getSize(),
                            stock.getColour(),
                            stock.getQty()
                    )
            );

        }

        if (saleItemQtyHolderDTOList.size() > 0) {

            saleItemHolderDTO.setAvailableColourList(availableColourList);
            saleItemHolderDTO.setSaleItemQtyHolderDTOList(saleItemQtyHolderDTOList);
            saleItemHolderDTO.setSaleItemImageHolderDTOList(saleItemImageHolderDTOList);

            saleItemHolderDTO.setICode(item.getICode());
            saleItemHolderDTO.setTags(getTags(item.getICode(), item.getCategory() , saleItemHolderDTO));
            saleItemHolderDTO.setDescription(item.getDescription());

            saleItemHolderDTO.setPrice(item.getPriceSell());

            return true;
        }

        return false;

    }


    private List<String> getTags(String iCode, Category category, SaleItemHolderDTO saleItemHolderDTO) {
        List<String> tagList = new ArrayList<>();

        if (category.equals(Category.SHOES)) {
            saleItemHolderDTO.setCategory("Shoes");

            String tempICode = iCode.split("0")[0];

            String occasion = tempICode.charAt(0) + "";
            String verities = tempICode.length() == 3 ? tempICode.charAt(1) + "" : tempICode.substring(1, 2);
            String gender = tempICode.charAt(tempICode.length() - 1) + "";


            tagList.add("#S");

            saleItemHolderDTO.setGender(gender.equals("M") ? "Male" : "Female");
            tagList.add("#" + saleItemHolderDTO.getGender());

            switch (occasion) {
                case "F":
                    occasion = "Formal";
                    tagList.add("#Formal");
                    break;
                case "C":
                    occasion = "Casual";
                    tagList.add("#Casual");
                    break;
                case "I":
                    occasion = "Industrial";
                    tagList.add("#Industrial");
                    break;
                case "S":
                    occasion = "Sport";
                    tagList.add("#Sport");
                    break;
            }

            switch (verities) {
                case "H":
                    verities = "Heel";
                    tagList.add("#Heel");
                    break;
                case "F":
                    verities = "Flats";
                    tagList.add("#Flats");
                    break;
                case "W":
                    verities = "Wedges";
                    tagList.add("#Wedges");
                    break;
                case "FF":
                    verities = "FlipFlops";
                    tagList.add("#FlipFlops");
                    break;
                case "SD":
                    verities = "Sandals";
                    tagList.add("#Sandals");
                    break;
                case "S":
                    verities = "Shoes";
                    tagList.add("#Shoes");
                    break;
                case "SL":
                    verities = "Slippers";
                    tagList.add("#Slippers");
                    break;
            }

            saleItemHolderDTO.setOccasion(occasion);
            saleItemHolderDTO.setVerities(verities);
        } else {
            saleItemHolderDTO.setCategory("Accessories");
            tagList.add("#Accessories");
        }

        return tagList;

    }

    private StockStatus statusCalc(int qty, int maxQty) {
        if (qty == 0) {
            return StockStatus.NOT_AVAILABLE;
        }

        if ((qty * 2) > maxQty) {
            return StockStatus.AVAILABLE;
        } else {
            return StockStatus.LOW;
        }

    }

    private ItemImage getItemImage(Item item, String imageId) {
        for (Stock stock : item.getStockList()) {
            if (stock.getItemImage().getImgId().equals(imageId)) {
                return stock.getItemImage();
            }

        }

        return null;
    }

    private void createImageIds(List<ItemImageDTO> itemImageDTOList) {
        imgHolderDTOList = new ArrayList<>();

        for (int i = 0; i < itemImageDTOList.size(); i++) {
            String generatedId = generateID.generateUUID();
            imgHolderDTOList.add(new ImgHolderDTO(itemImageDTOList.get(i).getItemImageId(), generatedId, itemImageDTOList.get(i).getImage()));

        }

    }

    private ItemImage getItemImage(String imgId) {
        for (ImgHolderDTO imgHolderDTO : imgHolderDTOList) {
            if (imgHolderDTO.getImgId().equals(imgId)) {
                ItemImage itemImage = new ItemImage();

                if (imgId.length() == 1) {
                    itemImage.setImgId(imgHolderDTO.getGeneratedImgId());

                } else {
                    itemImage.setImgId(imgId);

                }

                itemImage.setImg(imgHolderDTO.getImg());

                return itemImage;

            }

        }

        return null;

    }


}
