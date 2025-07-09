# Control Flow Graph (CFG) Analysis for CartOperations.java

## Overview
This document provides a detailed Control Flow Graph analysis of the `CartOperations.java` class, which contains cart-related database operations with multiple execution paths and error handling scenarios.

## Class Structure Analysis

### Public Methods (Entry Points)
1. `checkAddToCartNull(Connection conn, cart c)` - Lines 20-24
2. `checkAddToCartWithUser(Connection conn, cart c)` - Lines 30-34
3. `updateAddToCartNull(Connection conn, cart c)` - Lines 40-44
4. `updateAddToCartWithUser(Connection conn, cart c)` - Lines 50-54
5. `addToCartNull(Connection conn, cart c)` - Lines 60-64
6. `addToCartWithUser(Connection conn, cart c)` - Lines 70-74
7. `getSelectedCart(Connection conn)` - Lines 80-82
8. `getCartByCustomer(Connection conn, String customerName)` - Lines 88-91
9. `removeCartNull(Connection conn, cart c)` - Lines 97-100
10. `removeCartWithUser(Connection conn, cart c)` - Lines 106-109
11. `checkCartNull(Connection conn)` - Lines 115-117
12. `checkCartByUser(Connection conn, String userName)` - Lines 123-126
13. `deleteCartNull(Connection conn)` - Lines 132-134
14. `deleteCartByUser(Connection conn, String userName)` - Lines 140-143

### Private Helper Methods (Complex Logic)
1. `executeExistsCheck(Connection conn, String sql, Object[] parameters)` - Lines 147-160
2. `executeExistsCheck(Connection conn, String sql)` - Lines 162-170
3. `executeUpdate(Connection conn, String sql, Object[] parameters)` - Lines 172-185
4. `executeUpdate(Connection conn, String sql)` - Lines 187-196
5. `executeQuery(Connection conn, String sql, ResultSetMapper<cart> mapper)` - Lines 198-210
6. `executeQuery(Connection conn, String sql, Object[] parameters, ResultSetMapper<cart> mapper)` - Lines 212-228

## Detailed CFG Analysis

### 1. executeExistsCheck(Connection conn, String sql, Object[] parameters)

```
START (147)
    ↓
TRY BLOCK (148)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (149)
    ↓
FOR LOOP (150-152)
    ↓
ps.setObject(i + 1, parameters[i]) (151)
    ↓
TRY BLOCK (153)
    ↓
ResultSet rs = ps.executeQuery() (154)
    ↓
return rs.next() (155)
    ↓
END (156)
    ↓
CATCH BLOCK (157-159)
    ↓
logger.log(Level.SEVERE, "Error checking cart existence: " + sql, e) (158)
    ↓
return false (159)
    ↓
END (160)
```

**Decision Points:**
- Line 150: `for (int i = 0; i < parameters.length; i++)` - Loop condition
- Line 155: `return rs.next()` - ResultSet has next row
- Line 157: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. For loop → Continue iteration
4. For loop → Exit loop
5. ResultSet → Has next row
6. ResultSet → No more rows

### 2. executeExistsCheck(Connection conn, String sql)

```
START (162)
    ↓
TRY BLOCK (163-166)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (164)
    ↓
ResultSet rs = ps.executeQuery() (165)
    ↓
return rs.next() (166)
    ↓
END (167)
    ↓
CATCH BLOCK (168-169)
    ↓
logger.log(Level.SEVERE, "Error checking cart existence: " + sql, e) (168)
    ↓
return false (169)
    ↓
END (170)
```

**Decision Points:**
- Line 166: `return rs.next()` - ResultSet has next row
- Line 168: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. ResultSet → Has next row
4. ResultSet → No more rows

### 3. executeUpdate(Connection conn, String sql, Object[] parameters)

```
START (172)
    ↓
TRY BLOCK (173-182)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (174)
    ↓
FOR LOOP (175-177)
    ↓
ps.setObject(i + 1, parameters[i]) (176)
    ↓
int result = ps.executeUpdate() (178)
    ↓
IF (result > 0) (179)
    ↓
return 1 (180)
    ↓
ELSE
    ↓
return 0 (181)
    ↓
END (182)
    ↓
CATCH BLOCK (183-184)
    ↓
logger.log(Level.SEVERE, "Error executing cart update: " + sql, e) (183)
    ↓
return 0 (184)
    ↓
END (185)
```

**Decision Points:**
- Line 175: `for (int i = 0; i < parameters.length; i++)` - Loop condition
- Line 179: `result > 0` - Update successful
- Line 183: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. For loop → Continue iteration
4. For loop → Exit loop
5. result > 0 → return 1
6. result <= 0 → return 0

### 4. executeUpdate(Connection conn, String sql)

```
START (187)
    ↓
TRY BLOCK (188-194)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (189)
    ↓
int result = ps.executeUpdate() (190)
    ↓
IF (result > 0) (191)
    ↓
return 1 (192)
    ↓
ELSE
    ↓
return 0 (193)
    ↓
END (194)
    ↓
CATCH BLOCK (195-196)
    ↓
logger.log(Level.SEVERE, "Error executing cart update: " + sql, e) (195)
    ↓
return 0 (196)
    ↓
END (197)
```

**Decision Points:**
- Line 191: `result > 0` - Update successful
- Line 195: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. result > 0 → return 1
4. result <= 0 → return 0

### 5. executeQuery(Connection conn, String sql, ResultSetMapper<cart> mapper)

```
START (198)
    ↓
List<cart> results = new ArrayList<>() (199)
    ↓
TRY BLOCK (200-207)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (201)
    ↓
ResultSet rs = ps.executeQuery() (202)
    ↓
WHILE (rs.next()) (203)
    ↓
results.add(mapper.map(rs)) (204)
    ↓
END WHILE (205)
    ↓
END (206)
    ↓
CATCH BLOCK (207-208)
    ↓
logger.log(Level.SEVERE, "Error executing cart query: " + sql, e) (207)
    ↓
END (208)
    ↓
return results (209)
    ↓
END (210)
```

**Decision Points:**
- Line 203: `rs.next()` - ResultSet has next row
- Line 207: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. While loop → Continue iteration
4. While loop → Exit loop

### 6. executeQuery(Connection conn, String sql, Object[] parameters, ResultSetMapper<cart> mapper)

```
START (212)
    ↓
List<cart> results = new ArrayList<>() (213)
    ↓
TRY BLOCK (214-225)
    ↓
PreparedStatement ps = conn.prepareStatement(sql) (215)
    ↓
FOR LOOP (216-218)
    ↓
ps.setObject(i + 1, parameters[i]) (217)
    ↓
TRY BLOCK (219-223)
    ↓
ResultSet rs = ps.executeQuery() (220)
    ↓
WHILE (rs.next()) (221)
    ↓
results.add(mapper.map(rs)) (222)
    ↓
END WHILE (223)
    ↓
END (224)
    ↓
END (225)
    ↓
CATCH BLOCK (226-227)
    ↓
logger.log(Level.SEVERE, "Error executing parameterized cart query: " + sql, e) (226)
    ↓
END (227)
    ↓
return results (228)
    ↓
END (229)
```

**Decision Points:**
- Line 216: `for (int i = 0; i < parameters.length; i++)` - Loop condition
- Line 221: `rs.next()` - ResultSet has next row
- Line 226: `catch (Exception e)` - Exception handling

**Edges to Cover:**
1. Try block → Normal execution
2. Try block → Catch block (exception)
3. For loop → Continue iteration
4. For loop → Exit loop
5. While loop → Continue iteration
6. While loop → Exit loop

## Edge Coverage Analysis

### Total Edges Identified: 42

**Primary Decision Points:**
1. **Loop Conditions (6 edges):**
   - `for (int i = 0; i < parameters.length; i++)` - 3 instances
   - `while (rs.next())` - 2 instances

2. **Exception Handling (6 edges):**
   - `catch (Exception e)` - 6 instances

3. **Conditional Returns (4 edges):**
   - `result > 0` - 2 instances
   - `rs.next()` - 2 instances

4. **Method Entry Points (14 edges):**
   - Public method calls to helper methods

5. **Database Operation Results (12 edges):**
   - Success/failure scenarios for each operation type

### Edge Coverage Requirements (80% = 34 edges)

**Critical Edges to Cover:**
1. **Exception Handling Paths** (6 edges) - High priority
2. **Database Success/Failure Scenarios** (12 edges) - High priority
3. **Loop Entry/Exit Conditions** (6 edges) - Medium priority
4. **Conditional Return Paths** (4 edges) - Medium priority
5. **Method Entry Points** (14 edges) - Low priority

## Structural Testing Strategy

### Test Cases Required for 80% Edge Coverage:

#### 1. Exception Handling Tests
- **Database Connection Failure**
- **SQL Syntax Error**
- **Null Parameter Handling**
- **Invalid ResultSet Operations**

#### 2. Database Operation Tests
- **Successful Operations**
- **Failed Operations (0 rows affected)**
- **Empty ResultSet Scenarios**
- **Multiple Row ResultSet Scenarios**

#### 3. Loop Condition Tests
- **Empty Parameter Arrays**
- **Single Parameter Arrays**
- **Multiple Parameter Arrays**
- **Empty ResultSets**
- **Single Row ResultSets**
- **Multiple Row ResultSets**

#### 4. Conditional Return Tests
- **Update Operations with 0 affected rows**
- **Update Operations with >0 affected rows**
- **Query Operations with empty results**
- **Query Operations with results**

## CFG Visualization

```
[Entry Points] → [Public Methods] → [Helper Methods] → [Decision Points] → [Exit Points]
     ↓              ↓                    ↓                    ↓                ↓
  14 edges      14 edges            6 edges             8 edges          14 edges
```

**Total Paths: 56 possible execution paths**

**Minimum Test Cases for 80% Coverage: 28 test cases**

## Recommendations

1. **Focus on Exception Handling**: 6 critical test cases
2. **Database Operation Variations**: 12 test cases
3. **Edge Case Scenarios**: 6 test cases
4. **Normal Operation Paths**: 4 test cases

**Total: 28 test cases to achieve 80% edge coverage** 