package newio;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Sample flat file database to demonstrate RandomAccessFile usage.
 */

public class RandomAccessFileDemo {
    public static void main(String[] args) {
        PartsDB pdb = null;
        try {
            pdb = new PartsDB("parts.db");
            if (pdb.numRecs() == 0) {
                // Populate the database with records.
                pdb.append("1-9009-3323-4x", "Wiper Blade Micro Edge", 30, 2468);
                pdb.append("1-3233-44923-7j", "Parking Brake Cable", 5, 1439);
                pdb.append("2-3399-6693-2m", "Halogen Bulb H4 55/60W", 22, 813);
                pdb.append("2-599-2029-6k", "Turbo Oil Line O-Ring ", 26, 155);
                pdb.append("3-1299-3299-9u", "Air Pump Electric", 9, 20200);
            }
            dumpRecords(pdb);
            pdb.update(1, "1-3233-44923-7j", "Parking Brake Cable", 5, 1995);
            dumpRecords(pdb);
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            if (pdb != null)
                pdb.close();
        }
    }

    static void dumpRecords(PartsDB pdb) throws IOException {
        for (int i = 0; i < pdb.numRecs(); i++)
        {
            PartsDB.Part part = pdb.select(i);
            System.out.print(format(part.getPartnum(), PartsDB.PNUMLEN, true));
            System.out.print(" | ");
            System.out.print(format(part.getDesc(), PartsDB.DESCLEN, true));
            System.out.print(" | ");
            System.out.print(format("" + part.getQty(), 10, false));
            System.out.print(" | ");
            String s = part.getUnitCost() / 100 + "." + part.getUnitCost() %
            100;
            if (s.charAt(s.length() - 2) == '.') s += "0";
            System.out.println(format(s, 10, false));
        }
        System.out.println("Number of records = " + pdb.numRecs());
        System.out.println();
    }

    static String format(String value, int maxWidth, boolean leftAlign) {
        StringBuffer sb = new StringBuffer();
        int len = value.length();
        if (len > maxWidth) {
            len = maxWidth;
            value = value.substring(0, len);
        }
        if (leftAlign) {
            sb.append(value);
            for (int i = 0; i < maxWidth - len; i++)
                sb.append(" ");
        } else {
            for (int i = 0; i < maxWidth - len; i++)
                sb.append(" ");
            sb.append(value);
        }
        return sb.toString();
    }
}

class PartsDB {
    public static final int PNUMLEN = 20;
    public static final int DESCLEN = 30;
    public static final int QUANLEN = 4;
    public static final int COSTLEN = 4;
    private final static int RECLEN = 2 * PNUMLEN + 2 * DESCLEN + QUANLEN + COSTLEN;
    private RandomAccessFile raf;

    public PartsDB(String path) throws IOException {
        raf = new RandomAccessFile(path, "rw");
    }

    /**
     * Appends a part to flat file DB
     * @param partnum part number
     * @param partdesc part description
     * @param qty part quantity
     * @param ucost unit cost
     * @throws IOException
     */
    public void append(String partnum, String partdesc, int qty, int ucost) throws IOException {
        raf.seek(raf.length());
        write(partnum, partdesc, qty, ucost);
    }

    /**
     * Closes flat file i.e. random access file
     */
    public void close() {
        try {
            raf.close();
        } catch (IOException ioe) { System.err.println(ioe); }
    }

    /**
     * Returns no. of records in flat file DB
     * @return
     * @throws IOException
     */
    public int numRecs() throws IOException {
        return (int) raf.length() / RECLEN;
    }

    /**
     * Returns a part based on record number
     * @param recno record number
     * @return
     * @throws IOException
     */
    public Part select(int recno) throws IOException {
        if (recno < 0 || recno >= numRecs())
            throw new IllegalArgumentException(recno + " out of range");
        raf.seek(recno * RECLEN);
        return read();
    }

    /**
     * Updates an existing record
     * @param recno record number
     * @param partnum part number
     * @param partdesc part description
     * @param qty part quantity
     * @param ucost unit cost
     * @throws IOException
     */
    public void update(int recno, String partnum, String partdesc, int qty, int ucost) throws IOException {
        if (recno < 0 || recno >= numRecs())
            throw new IllegalArgumentException(recno + " out of range");
        raf.seek(recno * RECLEN);
        write(partnum, partdesc, qty, ucost);
    }

    /**
     * Reads and returns a part
     * @return
     * @throws IOException
     */
    private Part read() throws IOException {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < PNUMLEN; i++)
            sb.append(raf.readChar());
        String partnum = sb.toString().trim();
        sb.setLength(0);
        for (int i = 0; i < DESCLEN; i++)
            sb.append(raf.readChar());
        String partdesc = sb.toString().trim();
        int qty = raf.readInt();
        int ucost = raf.readInt();
        return new Part(partnum, partdesc, qty, ucost);
    }

    /**
     * Writes a part to flat file DB
     * @param partnum part number
     * @param partdesc part description
     * @param qty part quantity
     * @param ucost unit cost
     * @throws IOException
     */
    private void write(String partnum, String partdesc, int qty, int ucost) throws IOException {
        StringBuffer sb = new StringBuffer(partnum);
        if (sb.length() > PNUMLEN)
            sb.setLength(PNUMLEN);
        else if (sb.length() < PNUMLEN) {
            int len = PNUMLEN - sb.length();
            for (int i = 0; i < len; i++)
                sb.append(" ");
        }
        raf.writeChars(sb.toString());
        sb = new StringBuffer(partdesc);
        if (sb.length() > DESCLEN)
            sb.setLength(DESCLEN);

        else if (sb.length() < DESCLEN) {
            int len = DESCLEN - sb.length();
            for (int i = 0; i < len; i++)
                sb.append(" ");
        }
        raf.writeChars(sb.toString());
        raf.writeInt(qty);
        raf.writeInt(ucost);
    }

    // Part POJO
    public static class Part {
        private String partnum;
        private String desc;
        private int qty;
        private int ucost;

        public Part(String partnum, String desc, int qty, int ucost) {
            this.partnum = partnum;
            this.desc = desc;
            this.qty = qty;
            this.ucost = ucost;
        }

        String getDesc() {
            return desc;
        }

        String getPartnum() {
            return partnum;
        }

        int getQty() {
            return qty;
        }

        int getUnitCost() {
            return ucost;
        }
    }
}
