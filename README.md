# SpiralParty
> SpiralParty is a plugin that allows players to form parties in order to share resources, like experience and inventory.
> Parties also have a party chat "channel" to communicate with each other, without other players knowing what they are saying.
> Parties only exist per server session and while all party members are online. If a player logs out, gets kicked, or goes offline for any reason, that player will leave the party.
> Parties are essentially disbanded when all but one player leaves the party.
> Creating a party is simple, just invite someone to party with you. Once they accept the invite a new party will be created.
> Parties can only be so big, this is configurable in the config.yml file. By default, the max-party-size is set to 3, but can be set as high as 9 and as low as 2.

## Features
### Shared Experience
This feature has 3 modes of operation as well as ability to be turned off. These modes are:
- **split**
  > for any party member that collects experience, the total experience is divided equally and given to all players. e.g. if there are 3 members and member A collects 30 experience, each member receives 10 experience.
- **equal**
  > for any party member that collects experience, the total experience is duplicated for every party member. e.g. if there are 3 members and member A collects 30 experience, each member receives 30 experience.
- **party**
  > the players experience bars are temporarily stored in another part of memory, and all members share a common experience bar.
- **disabled**
  > this isn't a mode but using this keyword in the config.yml file, completely disables the shared experience features.

### Shared Inventory
This feature has 3 modes of operation as well as ability to be turned off. These modes are:
- **split**
  > for any party member that collects an ItemStack, the ItemStack's amount is split between each party member. e.g. if there are 3 members and member A collects 3 coal, each member gets 1 coal
- **equal**
  > for any party member that collects and ItemStack, the ItemStack is duplicated and given to each party member. (this mode is not recommended as it duplicates items)
- **party**
  > the players inventories are temporarily stored in another part of memory and replaced by a common inventory shared by all party members.
- **disabled**
  > this isn't a mode but using this keyword in the config.yml file, completely disables the shared inventory features.

## Commands
***Under Construction!***

## Permissions
***Under Construction!***

