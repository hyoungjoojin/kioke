"use client";

import SelectShelfCommand from "@/components/features/shelf/SelectShelfCommand";
import { useShelvesQuery } from "@/hooks/query/shelf";
import { useSelectedShelfIndex } from "@/hooks/store";
import { cn } from "@/lib/utils";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function ShelvesModal() {
  const router = useRouter();

  const { data: shelves } = useShelvesQuery();
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

  return (
    <SelectShelfCommand
      showArchive
      onSelect={(_, index) => {
        setSelectedShelfIndex(shelves, index);
        router.back();
      }}
      className={cn(
        "absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2",
        "w-2/5 h-1/2",
        "bg-white shadow-lg dark:bg-gray-700 border",
      )}
    />
  );
}
