# Control Flow Graph (CFG) Visual Diagrams for CartOperations.java

## 1. Main Class Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                    CartOperations.java                         │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │   Public Methods│  │ Helper Methods  │  │ Decision Points │ │
│  │   (Entry Points)│  │ (Complex Logic) │  │                 │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
│           │                     │                     │         │
│           ▼                     ▼                     ▼         │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │ • checkAddToCart│  │ • executeExists │  │ • Loop Conditions│ │
│  │ • updateAddToCar│  │ • executeUpdate │  │ • Exception Hand│ │
│  │ • addToCartNull │  │ • executeQuery  │  │ • Conditional   │ │
│  │ • removeCartNull│  │                 │  │ • Return Paths  │ │
│  │ • checkCartNull │  │                 │  │                 │ │
│  │ • deleteCartNull│  │                 │  │                 │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

## 2. executeExistsCheck Method CFG

```
                    START
                       │
                       ▼
                ┌─────────────┐
                │   TRY BLOCK │
                └─────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ PreparedStatement ps = │
        │ conn.prepareStatement()│
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │   FOR LOOP  │◄────┐
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ ps.setObject(i+1,      │ │
        │   parameters[i])        │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │   TRY BLOCK │     │
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ ResultSet rs =          │ │
        │ ps.executeQuery()       │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ return rs.next()        │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │     END     │     │
                └─────────────┘     │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │ CATCH BLOCK │     │
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ logger.log(SEVERE,      │ │
        │   "Error checking...")  │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ return false            │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │     END     │─────┘
                └─────────────┘
```

## 3. executeUpdate Method CFG

```
                    START
                       │
                       ▼
                ┌─────────────┐
                │   TRY BLOCK │
                └─────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ PreparedStatement ps = │
        │ conn.prepareStatement()│
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │   FOR LOOP  │◄────┐
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ ps.setObject(i+1,      │ │
        │   parameters[i])        │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ int result =            │ │
        │ ps.executeUpdate()      │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │ IF (result  │     │
                │    > 0)     │     │
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ return 1                │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ return 0                │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │     END     │─────┘
                └─────────────┘
                       │
                       ▼
                ┌─────────────┐
                │ CATCH BLOCK │
                └─────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ logger.log(SEVERE,      │
        │   "Error executing...") │
        └─────────────────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ return 0                │
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │     END     │
                └─────────────┘
```

## 4. executeQuery Method CFG

```
                    START
                       │
                       ▼
        ┌─────────────────────────┐
        │ List<cart> results =   │
        │ new ArrayList<>()       │
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │   TRY BLOCK │
                └─────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ PreparedStatement ps = │
        │ conn.prepareStatement()│
        └─────────────────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ ResultSet rs =          │
        │ ps.executeQuery()       │
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │ WHILE LOOP  │◄────┐
                │ (rs.next()) │     │
                └─────────────┘     │
                       │           │
                       ▼           │
        ┌─────────────────────────┐ │
        │ results.add(            │ │
        │   mapper.map(rs))       │ │
        └─────────────────────────┘ │
                       │           │
                       ▼           │
                ┌─────────────┐     │
                │     END     │─────┘
                └─────────────┘
                       │
                       ▼
                ┌─────────────┐
                │ CATCH BLOCK │
                └─────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ logger.log(SEVERE,      │
        │   "Error executing...") │
        └─────────────────────────┘
                       │
                       ▼
        ┌─────────────────────────┐
        │ return results          │
        └─────────────────────────┘
                       │
                       ▼
                ┌─────────────┐
                │     END     │
                └─────────────┘
```

## 5. Edge Coverage Map

```
┌─────────────────────────────────────────────────────────────────┐
│                    EDGE COVERAGE MAP                          │
├─────────────────────────────────────────────────────────────────┤
│                                                               │
│  Entry Points (14 edges):                                     │
│  ┌─ checkAddToCartNull ──┐                                    │
│  ├─ checkAddToCartWithUser ──┐                               │
│  ├─ updateAddToCartNull ──┐                                   │
│  ├─ updateAddToCartWithUser ──┐                              │
│  ├─ addToCartNull ──┐                                         │
│  ├─ addToCartWithUser ──┐                                     │
│  ├─ getSelectedCart ──┐                                        │
│  ├─ getCartByCustomer ──┐                                      │
│  ├─ removeCartNull ──┐                                         │
│  ├─ removeCartWithUser ──┐                                     │
│  ├─ checkCartNull ──┐                                          │
│  ├─ checkCartByUser ──┐                                        │
│  ├─ deleteCartNull ──┐                                         │
│  └─ deleteCartByUser ──┘                                       │
│                                                               │
│  Decision Points (8 edges):                                   │
│  ┌─ Loop Conditions (6) ──┐                                   │
│  │ ├─ for (i < length) ──┐                                    │
│  │ ├─ while (rs.next()) ──┐                                   │
│  │ └─ Exception handling ──┘                                   │
│  └─ Conditional Returns (2) ──┘                               │
│     ├─ result > 0 ──┐                                          │
│     └─ rs.next() ──┘                                           │
│                                                               │
│  Exit Points (14 edges):                                       │
│  ┌─ return true/false ──┐                                      │
│  ├─ return 0/1 ──┐                                             │
│  ├─ return List<cart> ──┐                                      │
│  └─ Exception returns ──┘                                       │
│                                                               │
└─────────────────────────────────────────────────────────────────┘
```

## 6. Test Path Coverage Matrix

```
┌─────────────────────────────────────────────────────────────────┐
│                    TEST PATH COVERAGE                          │
├─────────────────────────────────────────────────────────────────┤
│                                                               │
│  Path 1: Normal Execution (Success)                           │
│  Entry → Try Block → Database Op → Return Success             │
│                                                               │
│  Path 2: Exception Handling                                   │
│  Entry → Try Block → Exception → Catch → Return Error         │
│                                                               │
│  Path 3: Loop Execution                                       │
│  Entry → Try Block → Loop → Continue → Exit → Return          │
│                                                               │
│  Path 4: Empty ResultSet                                      │
│  Entry → Try Block → Query → Empty Result → Return Empty      │
│                                                               │
│  Path 5: Multiple Results                                     │
│  Entry → Try Block → Query → Multiple Results → Return List   │
│                                                               │
│  Path 6: Update Success                                       │
│  Entry → Try Block → Update → Rows Affected > 0 → Return 1   │
│                                                               │
│  Path 7: Update Failure                                       │
│  Entry → Try Block → Update → Rows Affected = 0 → Return 0   │
│                                                               │
└─────────────────────────────────────────────────────────────────┘
```

## 7. Complexity Metrics

```
┌─────────────────────────────────────────────────────────────────┐
│                    COMPLEXITY METRICS                         │
├─────────────────────────────────────────────────────────────────┤
│                                                               │
│  Cyclomatic Complexity: 15                                    │
│  - 6 decision points                                          │
│  - 6 exception handling paths                                 │
│  - 3 loop conditions                                          │
│                                                               │
│  Edge Coverage Target: 80% (34/42 edges)                     │
│  - Critical edges: 18                                         │
│  - Important edges: 12                                        │
│  - Optional edges: 12                                         │
│                                                               │
│  Test Cases Required: 28                                      │
│  - Exception tests: 6                                         │
│  - Database operation tests: 12                               │
│  - Edge case tests: 6                                         │
│  - Normal operation tests: 4                                  │
│                                                               │
└─────────────────────────────────────────────────────────────────┘
```

## 8. Coverage Strategy

```
┌─────────────────────────────────────────────────────────────────┐
│                    COVERAGE STRATEGY                          │
├─────────────────────────────────────────────────────────────────┤
│                                                               │
│  Phase 1: Critical Paths (High Priority)                     │
│  ├─ Exception handling scenarios                              │
│  ├─ Database connection failures                              │
│  └─ SQL execution errors                                      │
│                                                               │
│  Phase 2: Business Logic (Medium Priority)                   │
│  ├─ Successful operations                                     │
│  ├─ Failed operations                                         │
│  └─ Edge case scenarios                                       │
│                                                               │
│  Phase 3: Loop and Conditional (Medium Priority)             │
│  ├─ Empty parameter arrays                                    │
│  ├─ Single parameter arrays                                   │
│  └─ Multiple parameter arrays                                 │
│                                                               │
│  Phase 4: Normal Operations (Low Priority)                   │
│  ├─ Standard cart operations                                  │
│  ├─ Typical user scenarios                                    │
│  └─ Expected behavior paths                                   │
│                                                               │
└─────────────────────────────────────────────────────────────────┘
``` 