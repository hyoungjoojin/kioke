import { Button } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import { LibraryBig } from "lucide-react";
import Link from "next/link";

interface ShowShelfListButtonProps {
  className?: string;
}

export default function ShowShelfListButton({
  className,
}: ShowShelfListButtonProps) {
  return (
    <Link href="/shelves">
      <Button variant="outline" className={cn(className)}>
        <LibraryBig />
      </Button>
    </Link>
  );
}
