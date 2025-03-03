"use client";

import { Button } from "@/components/ui/button";
import { useShelvesQuery } from "@/hooks/query";
import { useSelectedShelf } from "@/hooks/store";
import { SquarePen } from "lucide-react";
import Link from "next/link";

export default function AddJournalButton() {
  const { data } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

  console.log(selectedShelf);
  if (selectedShelf?.isArchive) {
    return null;
  }

  return (
    <Link href="/journal/new">
      <Button variant="outline">
        <SquarePen />
      </Button>
    </Link>
  );
}
