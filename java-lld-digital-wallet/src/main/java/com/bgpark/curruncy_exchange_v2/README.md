# Currency Exchange Project

## Requirements
- [x] User can exchange conversionRate from one currency to another currency
- [x] User can see the exchange rate
- [x] User can see the history of exchange

## Key Point
### 1. BigDecimal
**BigDecimal with String**
Avoid using double to avoid precision error when create BigDecimal
That can occur with floating point literal
Because computer can not represent 0.3 precisely in binary