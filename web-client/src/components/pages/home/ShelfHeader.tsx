"use client";

import { useBoundStore } from "@/components/providers/StoreProvider";
import { GetShelvesResponseBody } from "@/types/server/shelf";
import { useEffect } from "react";

export default function ShelfHeader({
  shelves,
}: {
  shelves: GetShelvesResponseBody;
}) {
  const setShelves = useBoundStore((state) => state.actions.setShelves);
  const selectedShelf = useBoundStore((state) =>
    state.actions.getSelectedShelf(),
  );

  useEffect(() => {
    setShelves(shelves);
  }, [shelves, setShelves]);

  return <h1 className="text-3xl my-10">{selectedShelf?.name}</h1>;
}
