+---------------------------+
|       Item (DTO)         |
+---------------------------+
| - name: String           |
| - price: double          |
| - category: String       |
+---------------------------+

+---------------------------+
|   ItemBillRequest (DTO)   |
+---------------------------+
| - items: List<Item>      |
| - userType: UserType     |
| - customerTenure: int    |
| - targetCurrency: String |
+---------------------------+

+-----------------------------+
|  ItemBillResponse (DTO)     |
+-----------------------------+
| - originalTotal: double     |
| - discountedTotal: double   |
| - convertedTotal: double    |
| - targetCurrency: String    |
+-----------------------------+

+---------------------------+
|    UserType (Enum)       |
+---------------------------+
| + EMPLOYEE               |
| + AFFILIATE              |
| + CUSTOMER               |
+---------------------------+

+-------------------------------+
|   BillDiscountService         |
+-------------------------------+
| + applyDiscount(             |
|   items: List<Item>,         |
|   userType: UserType,        |
|   tenure: int): double       |
+-------------------------------+

+-------------------------------+
|   CurrencyExchangeService     |
+-------------------------------+
| + convert(                   |
|   amount: double,            |
|   from: String,              |
|   to: String): double        |
+-------------------------------+

+-------------------------------+
|      BillController          |
+-------------------------------+
| + calculateBill(...):        |
|   ItemBillResponse           |
+-------------------------------+
         ▲             ▲
         |             |
    uses ↓        uses ↓
+-------------------------------+
|  BillDiscountService          |
|  CurrencyExchangeService      |
+-------------------------------+

+------------------------------------+
|     GlobalExceptionHandler         |
+------------------------------------+
| @ControllerAdvice                  |
| + handleMethodArgumentNotValid(...)|
| + handleCustomExceptions(...)      |
| + handleAllExceptions(...)         |
+------------------------------------+

