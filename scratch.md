A mod which lets you store certain items in a decorative and accessible way
- Right-clicking an empty spot with a valid item will place it
- Right-clicking that item will take it again
	- Some things might have special right-click functionality, in which case shift-right-click activates it
- Right clicking an empty spot without a valid item opens the UI for inventory interactions
- Config file can define presets for how any given item should render (scale/position/rotation)
	- Preset configs available
	- Multiple configs can load from a folder
	- probably JSON format
- In game configuration item can open a special UI
	- Still interact like a normal inventory
	- Each item can be configured for scale/pos/rotation
	- Can save the configuration either to the slot or to the item


Styles:
- Shelf (Bibliocraft style) - 2x2
- Open Shelf - 2x2
- Half Shelf - 2x1 (Upper/Mid/Lower)
- Weapon rack - 4x1
- Pegboard - 2x2 - maybe work like chests on a larger scale? 
- Jukebox - Only holds records (16). Can select and play from any. Maybe playlists? 
- Fillables:
	- Holds multiple stacks of one type of item
	- Right click with the item to store, right click with empty to withdraw
	- Basically like barrels/drawers
	- Can be:
		- Powder Basin (powders: redstone, glowstone, gunpowder etc)
		- Ore Basin (raw ores, custom models)
		- Ingot (stacks of ingots) - one ingot = 1 stack
		- Quiver for arrows
		- Spool for string
		- Pile of sticks
		- Planter for saplings/flowers
		- Item Basin (takes any item, but only half quantity of the rest)
		- Block pallet (takes any block)
	- Movable, as a mounted entity with reduced speed (pallet truck!)


Special items:
- Bookcase - shows 4 books and splits a shelf space into 4 for them
	- Without this, a book is placed on a stand and displayed front on
	- Supports special books
	- Can go in shelf, half and open shelf
- Record Case - similar to bookcase, for records
	- 8 per case? or is that too precise?
	- Can go in a half shelf
	- Automatically included in jukebox
- Special books - can be named and disguised
	- Can go in anything a Bookcase goes into, including a Bookcase itself
	- Redstone book - when right-clicked, functions as a button
	- Hollowed book - Can place one unstacked item inside (no blocks)

Special renders:
- Books - both in a case and alone, have a 3D model
- Potions/bottles - A 3D bottle model with fluid coloured to match (Splash and lingering have different textures so different models) (dont forget bottle o' enchanting)
- Buckets - as above
- Rails, Ladders, Ingots, Iron Bars/Glass panes, Candles (all variation models depending on quantity)
- Items with special blocks should use the block:
	- Redstone repeater/Comparitor
	- Torch and variants
	- Lever
	- Tripwire hook
	- Cauldron
	- Minecarts/Boats
	- Armor stand
	- Bell
	- Lantern
	- Chain
	- Brewing stand
	- End crystal
	- Flower pot
	- Sign (example of custom rotation)
	- Armour?
	- Cake

Mod specific special renders:
- Vics point blank
