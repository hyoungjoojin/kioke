"use client";

import { useShelf, useShelfActions } from "@/hooks/store";
import { GetShelvesResponseBody } from "@/types/server/shelf";
import { useEffect } from "react";

export default function ShelfHeader({
  shelves,
}: {
  shelves: GetShelvesResponseBody;
}) {
  const { selectedShelf } = useShelf();
  const { setShelves } = useShelfActions();

  useEffect(() => {
    setShelves(shelves);
  }, [shelves, setShelves]);

  return <h1 className="text-3xl my-10">{selectedShelf?.name}</h1>;
}
