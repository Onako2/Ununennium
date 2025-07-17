# Ununennium

Cache selectors in commands to let Minecraft go brr

Ununennium caches selectors (predicates) you use when executing Minecraft brigadier commands.
This may greatly improve the performance of function datapacks that execute many commands in one tick

## FAQ
- **Q: Will anything break?**
  - **A:** It depends. In rare cases, caching may cause unexpected behavior if you rely on selector results changing within the same tick. For example:
    ```mcfunction
    # This will now load it into the cache
    execute as @e[tag=nice_tag] run say hello

    # We'll add a tag here
    tag @p add nice_tag

    # We'll try to execute this here and it will just not do it because of the cache.
    # If it was executed next tick it would've worked because of the refreshed cache!
    execute as @e[tag=nice_tag] run say THIS WILL ONLY BE VISIBLE AT THE NEXT EXECUTION
    ```
    This only affects commands run within the same tick. On the next tick, the cache refreshes and the command will work as expected.

- **Q: Will this change my datapacks or world?**
  - **A:** No, the mod does not touch any files or modify your datapacks/world.

- **Q: Datapack XY doesn't work, what to do?**
  - **A:** Please [create an issue](https://github.com/Onako2/Ununennium/issues) so it is easier to track!

## Contributing
Contributions, bug reports, and suggestions are welcome! Please open an issue or pull request on GitHub.

## License
This project is licensed under the terms of the MIT License. See [LICENSE](LICENSE) for details.
