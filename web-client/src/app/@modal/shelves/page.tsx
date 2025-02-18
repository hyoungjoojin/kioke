"use client";

import { useBoundStore } from "@/components/providers/StoreProvider";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { useShelf, useShelfActions } from "@/hooks/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function ShelvesModal() {
  const router = useRouter();

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

  const { shelves } = useShelf();
  const { setSelectedShelf } = useShelfActions();

  return (
    <Command
      onClick={(e) => {
        e.stopPropagation();
      }}
      className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-2/5 h-1/2 border border-black"
    >
      <CommandInput placeholder="Enter a shelf to move to" />
      <CommandList>
        <CommandEmpty>No results found.</CommandEmpty>

        <CommandGroup heading="Shelves">
          {shelves &&
            shelves.map((shelf, index) => (
              <CommandItem
                key={index}
                onSelect={() => {
                  setSelectedShelf(index);
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
