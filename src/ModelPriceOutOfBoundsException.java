public class ModelPriceOutOfBoundsException extends RuntimeException {
        private final double price;

        public ModelPriceOutOfBoundsException(double price) {
            super("Некорректная цена модели: " + price);
            this.price = price;
        }

        public ModelPriceOutOfBoundsException(double price, String message) {
            super(message);
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }