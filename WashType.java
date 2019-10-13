class WashType
{

    String washName;
    double price;

    public WashType(String typeName, double Price)
    {
        washName = typeName;
        price = Price;
    }

    private String getWashName(){ return washName; }

    private double getPrice(){ return price; }

    @Override
    public String toString()
    {
        if(washName != null)
        {
            return "Vaske type" + washName + "\t Pris :" + price;
        }

        /* denne del af koden køres kun hvis washName/price null/0 */
        return "Vask Udefineret";
    }
}