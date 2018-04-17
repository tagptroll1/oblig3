package Code;

import javafx.beans.property.SimpleIntegerProperty;

public class InvoiceItem {
    private final SimpleIntegerProperty invoiceId;
    private final SimpleIntegerProperty productId;

    public InvoiceItem(int invoice, int product) {
        this.invoiceId = new SimpleIntegerProperty(invoice);
        this.productId = new SimpleIntegerProperty(product);
    }

    public int getInvoiceId() {
        return invoiceId.get();
    }

    public SimpleIntegerProperty invoiceIdProperty() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId.set(invoiceId);
    }

    public int getProductId() {
        return productId.get();
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }
}
