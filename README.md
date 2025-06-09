# Farmer SellCommand Module

Automatically sells stocked items on demand via a simple `/farmer sell` command tied to your Farmer regions.

---

## 📦 Installation

1. Drop the `SellCommand` folder into `plugins/Farmer/modules/`  
2. Restart your server  
3. A `config.yml` and matching language file will be generated under `plugins/Farmer/modules/SellCommand/`

---

## ⚙️ Features

- **On-Demand Selling**  
  Players can sell a specific stocked item or everything at once with a single command.

- **Region-Aware**  
  Works only when the player is inside their own Farmer region (unless bypass permission).

- **Configurable “Sell All” Aliases**  
  Define multiple keywords (`all`, `whole`, `hepsi`, etc.) to trigger “sell everything.”

- **Toggleable Module**  
  Enable or disable the entire command via one `status` setting in the module’s config.

---

## 🔧 Configuration (config.yml)

- **status**: `true` / `false` — enable or disable the SellCommand module.  
- **sellAllCommands**: list of keywords that trigger selling all stocked items (e.g. `all`, `whole`, `hepsi`).

_All options include inline comments in the generated config.yml._

---

## 🛠 Commands & Permissions

### /farmer sell <item>  
Sell one unit of the specified stocked item  
- Requires `farmer.sell.<item>` permission  

### /farmer sell <alias> (e.g. `/farmer sell all`)  
Sell all stocked items at once  
- Requires `farmer.sell.all` permission  

### Additional Checks  
- Players with `farmer.admin` or region-owner rights can always sell.  
- If you lack permission, you’ll receive the standard “noPerm” language message.

---

## 🤝 Contributing

1. Fork this repo  
2. Make your improvements or fixes  
3. Open a pull request against `develop`  

Please follow the existing style and update documentation as needed.

---
