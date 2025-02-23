"use client";

import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { useShelvesQuery } from "@/hooks/query";
import { useSelectedShelfIndex } from "@/hooks/store";
import { cn } from "@/lib/utils";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function ShelvesModal() {
  const router = useRouter();

  const { data } = useShelvesQuery();
  const { setSelectedShelfIndex } = useSelectedShelfIndex();

  useEffect(() => {
    const clickHandler = () => {
      router.back();
    };

    const keyDownHandler = (e: KeyboardEvent) => {
      if (e.key === "Escape") router.back();
    };

    window.addEventListener("click", clickHandler);
    window.addEventListener("keydown", keyDownHandler);

    return () => {
      window.removeEventListener("click", clickHandler);
      window.removeEventListener("keydown", keyDownHandler);
    };
  });

  const shelves = data?.shelves;

  return (
    <Command
      onClick={(e) => {
        e.stopPropagation();
      }}
      className={cn(
        "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2",
        "w-2/5 h-1/2",
        "bg-white shadow-lg dark:bg-gray-700 border",
      )}
    >
      <CommandInput placeholder="Enter a shelf to move to" />
      <CommandList>
        <CommandGroup heading="Shelves">
          <CommandEmpty>No results found.</CommandEmpty>
          {shelves &&
            shelves.map((shelf, index) => (
              <CommandItem
                key={index}
                onSelect={() => {
                  setSelectedShelfIndex(shelves, index);
                  router.back();
                }}
              >
                {shelf.name}
              </CommandItem>
            ))}
        </CommandGroup>
      </CommandList>
    </Command>
  );
}
