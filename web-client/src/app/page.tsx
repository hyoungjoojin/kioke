import Image from "next/image";

export default function Home() {
  return (
    <div>
      <Image
        aria-hidden
        src="/assets/logo/kioke-logo-nocircle.svg"
        alt="kioke"
        width={100}
        height={100}
      />
    </div>
  );
}
