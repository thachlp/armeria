# How to generate GraalVM native image configuration

## Step 1: Generate native image configuration files

First, generate the native image configuration files by running the tests annotated with
`GenerateNativeImageTrace` with [`native-image-agent`](https://www.graalvm.org/latest/reference-manual/native-image/metadata/AutomaticMetadataCollection/).

```
$ ../gradlew nativeImageConfig
```

The generation process consists of the following 3 sub-steps:

1. `processNativeImageTraces` task processes the native image traces generated by running the tests annotated 
   with `@GenerateNativeImageTrace`. It generates the native image configuration at `build/step-1-process-native-image-traces`.
2. `simplifyNativeImageConfig` task simplifies the native image configuration generated by `processNativeImageTraces`.
   It generates the cleaned native image configuration at `build/step-2-clean-native-image-config`.
3. `nativeImageConfig` task merges the base configuration at `src/base-config` and the cleaned native image
   configuration at `build/step-2-clean-native-image-config`. It generates the final native image configuration
   at `build/step-3-final-native-image-config`.

## Step 2: Review the generated native image configuration files and update the build configuration

Then, review the generated native image configuration files at `build/step-3-final-native-image-config`
to ensure it doesn't contain any irrelevant entries. You can remove such entries by updating the following places:

- `processNativeImageTraces` task:
  - the [caller-based filters](https://www.graalvm.org/latest/reference-manual/native-image/metadata/AutomaticMetadataCollection/#caller-based-filters)
    at `src/trace-filters/caller-filter.json` to filter out entries by call sites. 
- `simplifyNativeImageConfig` task
  - `excludedTypePatterns` and `excludedResourcePatterns` properties in `build.gradle.kts` to filter out
    entries by call targets.
- `nativeImageConfig` task
  - the base configuration at `src/base-config` to add entries manually

You can also annotate more tests classes with `@GenerateNativeImageTrace` if you find them trigger the code path
that should be evaluated by `native-image-agent`.

If you updated the build configuration or added or removed `@GenerateNativeImageTrace` annotation,
repeat the process from beginning.

## Step 3: Compare the generated native image configuration to the current configuration and update the current configuration

If you didn't have to update the build configuration, compare the generated native image configuration to
the current configuration at `core/src/main/resources/META-INF/native-image/`. If you find any differences,
update the current configuration accordingly and send a pull request.