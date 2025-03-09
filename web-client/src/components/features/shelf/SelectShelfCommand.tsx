"use client";

import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { useShelvesQuery } from "@/hooks/query/shelf";
import { Shelf } from "@/types/primitives/shelf";
import { CommandSeparator } from "cmdk";
import { Trash2 } from "lucide-react";

interface SelectShelfCommandProps {
  onSelect: (selectedShelf: Shelf, selectedShelfIndex: number) => void;
  showArchive?: boolean;
  className?: string | undefined;
}

const SelectShelfCommand = ({
  onSelect,
  showArchive = false,
  className,
}: SelectShelfCommandProps) => {
  const { data: shelves } = useShelvesQuery();

  const archiveIndex = shelves?.findIndex((shelf) => shelf.isArchive);

  return (
    <Command
      onClick={(e) => {
        e.stopPropagation();
      }}
      className={className}
    >
      <CommandInput autoFocus placeholder="Enter a shelf to move to" />
      <CommandList>
        <CommandGroup>
          <CommandEmpty>No results found.</CommandEmpty>
          {shelves &&
            shelves.map((shelf, index) => {
              if (shelf.isArchive) {
                return null;
              }

              return (
                <CommandItem
                  key={index}
                  onSelect={() => {
                    onSelect(shelf, index);
                  }}
                >
                  {shelf.name}
                </CommandItem>
              );
            })}
        </CommandGroup>

        {showArchive && (
          <>
            <CommandSeparator />
            <CommandGroup heading="">
              {shelves && archiveIndex && archiveIndex !== -1 && (
                <CommandItem
                  onSelect={() => {
                    onSelect(shelves[archiveIndex], archiveIndex);
                  }}
                >
                  <Trash2 />
                  Archive
                </CommandItem>
              )}
            </CommandGroup>
          </>
        )}
      </CommandList>
    </Command>
  );
};

export default SelectShelfCommand;
