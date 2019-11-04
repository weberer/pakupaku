package Controller;

public class UpdateEvent
{
    private int updateNum;
    private UpdateEvent(UpdateEventBuilder builder)
    {

        if(builder.updateNum != null)
            this.updateNum = builder.updateNum;
    }

    public int getUpdateNumber()
    {
        return this.updateNum;
    }

    public static class UpdateEventBuilder
    {
        private Integer updateNum;

        public UpdateEventBuilder()
        {

        }

        public UpdateEventBuilder withUpdateNumber(int updateNum)
        {
            this.updateNum = updateNum;
            return this;
        }

        public UpdateEvent build()
        {
            return new UpdateEvent(this);
        }
    }
}
