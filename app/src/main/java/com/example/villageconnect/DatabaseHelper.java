package com.example.villageconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "VillageConnect.db";
    private static final int DB_VERSION = 4;   // increase version

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ================= USERS =================

        db.execSQL("CREATE TABLE users(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fullname TEXT," +
                "username TEXT UNIQUE," +
                "phone TEXT," +
                "email TEXT," +
                "password TEXT," +
                "gender TEXT," +
                "dob TEXT," +
                "occupation TEXT," +
                "income TEXT," +
                "state TEXT," +
                "district TEXT," +
                "language TEXT," +
                "disability TEXT," +
                "land TEXT," +
                "family_size TEXT," +
                "role TEXT)");

        // ================= SCHEMES =================

        db.execSQL("CREATE TABLE schemes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "scheme_name TEXT," +
                "category TEXT," +
                "state TEXT," +
                "eligibility TEXT," +
                "benefits TEXT," +
                "deadline TEXT)");

        insertDefaultSchemes(db);

        // ================= DOCUMENTS =================

        insertDefaultSchemes(db);

// ================= DOCUMENTS =================

        db.execSQL("CREATE TABLE documents (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "document_type TEXT," +
                "extracted_text TEXT," +
                "verified INTEGER DEFAULT 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE IF NOT EXISTS documents (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "document_type TEXT," +
                    "extracted_text TEXT," +
                    "verified INTEGER DEFAULT 0)");
        }

    }
    // =====================================================
    // INSERT DEFAULT SCHEMES
    // =====================================================

    private void insertDefaultSchemes(SQLiteDatabase db) {

        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM-Kisan','Farmer','Karnataka','Farmer','₹6000 yearly assistance','31 Dec 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Ayushman Bharat','Health','Karnataka','Income below 2 lakh','Free health coverage','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Crop Insurance','Agriculture','Karnataka','Farmer','Crop loss protection','15 Nov 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Student Scholarship','Education','Karnataka','Student','Scholarship assistance','30 Oct 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Kisan Credit Card','Farmer','Karnataka','Farmer','Low-interest agricultural loans','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Soil Health Card','Agriculture','Karnataka','Farmer','Free soil testing','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'National Livestock Mission','Livestock','Karnataka','Livestock owner','Financial support for livestock','20 Dec 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Pradhan Mantri Fasal Bima','Agriculture','Karnataka','Farmer','Crop insurance benefits','15 Nov 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM Awas Yojana','Housing','Karnataka','Low-income family','Affordable housing assistance','31 Mar 2027')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Jal Jeevan Mission','Water','Karnataka','Rural household','Tap water connection','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Ujjwala Yojana','Women','Karnataka','BPL Women','Free LPG connection','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Sukanya Samriddhi','Girl Child','Karnataka','Girl below 10 years','High-interest savings scheme','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Beti Bachao Beti Padhao','Women','Karnataka','Girl child','Education and welfare support','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Janani Suraksha Yojana','Health','Karnataka','Pregnant women','Cash assistance for delivery','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM Matru Vandana','Women','Karnataka','Pregnant women','Maternity benefit','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'National Pension Scheme','Pension','Karnataka','Citizen 18+','Retirement savings','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Atal Pension Yojana','Pension','Karnataka','Unorganized workers','Guaranteed pension','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Old Age Pension','Pension','Karnataka','Senior Citizen','Monthly pension','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Widow Pension Scheme','Pension','Karnataka','Widowed women','Monthly financial aid','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Disability Pension','Disability','Karnataka','Disabled citizen','Monthly pension','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Skill India Mission','Employment','Karnataka','Youth','Free skill training','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM Kaushal Vikas','Employment','Karnataka','Unemployed youth','Skill certification','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Digital India Internship','Employment','Karnataka','Graduate','Internship opportunity','30 Sep 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Mudra Loan','Business','Karnataka','Small entrepreneur','Business loan up to 10 lakh','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Stand Up India','Business','Karnataka','Women & SC/ST entrepreneur','Startup loan','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Startup Karnataka','Startup','Karnataka','Innovators','Funding & mentorship','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM SVANidhi','Business','Karnataka','Street Vendor','Working capital loan','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'One Nation One Ration','Food','Karnataka','Ration Card Holder','Portable ration benefits','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Food Security Scheme','Food','Karnataka','BPL Family','Subsidized food grains','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Anna Bhagya','Food','Karnataka','BPL Family','Additional rice allocation','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Free Bicycle Scheme','Education','Karnataka','School Student','Free bicycle','30 Jun 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Laptop Distribution','Education','Karnataka','College Student','Free laptop assistance','31 Jul 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Hostel Scholarship','Education','Karnataka','SC/ST Student','Hostel fee support','31 Aug 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Minority Scholarship','Education','Karnataka','Minority Student','Educational scholarship','30 Sep 2026')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Post Matric Scholarship','Education','Karnataka','College Student','Fee reimbursement','31 Oct 2026')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'E-Shram Card','Labour','Karnataka','Unorganized Worker','Insurance & welfare','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Building Worker Welfare','Labour','Karnataka','Construction Worker','Medical & education support','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Labour Insurance','Labour','Karnataka','Registered Worker','Accident insurance','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Handloom Weaver Scheme','Textile','Karnataka','Weaver','Financial assistance','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Fishermen Welfare','Fisheries','Karnataka','Fisherman','Equipment subsidy','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Rural Toilet Mission','Sanitation','Karnataka','Rural Family','Household toilet construction','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Solar Pump Subsidy','Energy','Karnataka','Farmer','Subsidized solar pump','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'PM Kusum','Energy','Karnataka','Farmer','Solar agriculture subsidy','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Rainwater Harvesting','Environment','Karnataka','House Owner','Water conservation subsidy','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Green Village Mission','Environment','Karnataka','Village Panchayat','Tree plantation grants','Always Open')");

        db.execSQL("INSERT INTO schemes VALUES(NULL,'Self Help Group Loan','Women','Karnataka','SHG Member','Low-interest group loan','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Mahila Udyam Nidhi','Women','Karnataka','Women Entrepreneur','Business financial support','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Rural Artisan Scheme','Craft','Karnataka','Rural Artisan','Tool & equipment subsidy','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Village Entrepreneurship Program','Business','Karnataka','Young Entrepreneur','Startup mentoring','Always Open')");
        db.execSQL("INSERT INTO schemes VALUES(NULL,'Chief Minister Health Relief','Health','Karnataka','Low-income patient','Medical financial assistance','Always Open')");
    }

    // =====================================================
    // REGISTER USER
    // =====================================================

    public boolean insertUser(String fullname,
                              String username,
                              String phone,
                              String email,
                              String password,
                              String gender,
                              String dob,
                              String occupation,
                              String income,
                              String state,
                              String district,
                              String language,
                              String disability,
                              String land,
                              String familySize,
                              String role) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fullname", fullname);
        cv.put("username", username);
        cv.put("phone", phone);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("gender", gender);
        cv.put("dob", dob);
        cv.put("occupation", occupation);
        cv.put("income", income);
        cv.put("state", state);
        cv.put("district", district);
        cv.put("language", language);
        cv.put("disability", disability);
        cv.put("land", land);
        cv.put("family_size", familySize);
        cv.put("role", role);

        long result = db.insert("users", null, cv);
        db.close();

        return result != -1;
    }

    // =====================================================
    // LOGIN
    // =====================================================

    public boolean loginUser(String username,
                             String password,
                             String role) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=? AND role=?",
                new String[]{username, password, role});

        boolean success = cursor.moveToFirst();

        cursor.close();
        db.close();

        return success;
    }

    // =====================================================
    // GET USER
    // =====================================================

    public Cursor getUser(String username) {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM users WHERE username=?",
                new String[]{username});
    }

    public boolean updateUserProfile(String username,
                                     String fullname,
                                     String phone,
                                     String occupation,
                                     String income,
                                     String state,
                                     String district){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("fullname", fullname);
        cv.put("phone", phone);
        cv.put("occupation", occupation);
        cv.put("income", income);
        cv.put("state", state);
        cv.put("district", district);

        int result = db.update(
                "users",
                cv,
                "username=?",
                new String[]{username}
        );

        return result > 0;
    }

    // =====================================================
    // TOTAL USERS
    // =====================================================

    public int getTotalUsers() {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM users", null);

        int total = 0;

        if(c.moveToFirst())
            total = c.getInt(0);

        c.close();

        return total;
    }

    // =====================================================
    // VOLUNTEER USERS
    // =====================================================

    public Cursor getAllCitizens(){

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM users WHERE role='User'",
                null);
    }

    // =====================================================
    // INSERT SCHEME
    // =====================================================

    public boolean insertScheme(String name,
                                String category,
                                String state,
                                String eligibility,
                                String benefits,
                                String deadline){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("scheme_name",name);
        cv.put("category",category);
        cv.put("state",state);
        cv.put("eligibility",eligibility);
        cv.put("benefits",benefits);
        cv.put("deadline",deadline);

        long result = db.insert("schemes",null,cv);

        db.close();

        return result!=-1;
    }

    // =====================================================
    // ALL SCHEMES
    // =====================================================

    public Cursor getSchemes(){

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery("SELECT * FROM schemes",null);
    }

    // =====================================================
    // STATE SCHEMES
    // =====================================================

    public Cursor getSchemesByState(String state){

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM schemes WHERE state=?",
                new String[]{state});
    }

    // =====================================================
    // AI RECOMMENDED SCHEMES
    // =====================================================

    public Cursor getRecommendedSchemes(String state,
                                        String occupation,
                                        String income){

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM schemes WHERE state=? AND (category=? OR eligibility=? OR eligibility='Farmer' OR eligibility='Student')",
                new String[]{state,occupation,occupation});
    }


// =====================================================
// DOCUMENTS
// =====================================================

    public boolean saveDocument(String username,
                                String documentType,
                                String extractedText,
                                boolean verified) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("username", username);
        cv.put("document_type", documentType);
        cv.put("extracted_text", extractedText);
        cv.put("verified", verified ? 1 : 0);

        long result = db.insert("documents", null, cv);

        return result != -1;
    }

    public Cursor getDocuments(String username) {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM documents WHERE username=?",
                new String[]{username}
        );
    }
    public boolean updateDocumentVerification(
            String username,
            String documentType,
            boolean verified) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("verified", verified ? 1 : 0);

        int result = db.update(
                "documents",
                values,
                "username=? AND document_type=?",
                new String[]{username, documentType}
        );

        return result > 0;
    }

}