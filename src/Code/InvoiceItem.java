package Code;

public class InvoiceItem {
    // TODO User SimpleXProperty

    private int invoiceId;
    private int productId;

    public InvoiceItem() {}

    public InvoiceItem(int invoice, int product) {
        this.invoiceId = invoice;
        this.productId = product;
    }

    public int getInvoice() {
        return invoiceId;
    }

    public void setInvoice(int invoice) {
        this.invoiceId = invoice;
    }

    public int getProduct() {
        return productId;
    }

    public void setProduct(int product) {
        this.productId = product;
    }
}
