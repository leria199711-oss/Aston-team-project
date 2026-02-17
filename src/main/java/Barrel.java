public class Barrel {
    private final double volume;
    private final String material;
    private final String storedMaterial;

    private Barrel(Builder builder) {
        this.volume = builder.volume;
        this.material = builder.material;
        this.storedMaterial = builder.storedMaterial;
    }

    public double getVolume() {
        return volume;
    }

    public String getMaterial() {
        return material;
    }

    public String getStoredMaterial() {
        return storedMaterial;
    }

    public static class Builder {
        private double volume;
        private String material;
        private String storedMaterial;

        public Builder setVolume(double volume) {
            this.volume = volume;
            return this;
        }
        public Builder setMaterial(String material) {
            this.material = material;
            return this;
        }
        public Builder setStoredMaterial(String storedMaterial) {
            this.storedMaterial = storedMaterial;
            return this;
        }
        public Barrel build(){
            if (volume < 0) {
                throw new IllegalArgumentException("Volume must be greater than 0");
            }
            if (storedMaterial == null || storedMaterial.isEmpty()) {
                throw new IllegalArgumentException("Stored material cannot be null or empty");
            }
            if (material == null || material.isEmpty()) {
                throw new IllegalArgumentException("Material cannot be null or empty");
            }
            return new Barrel(this);
        }

    }
}
